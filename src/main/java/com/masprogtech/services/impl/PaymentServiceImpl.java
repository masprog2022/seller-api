package com.masprogtech.services.impl;

import com.masprogtech.dtos.PaymentConfirmationDTO;
import com.masprogtech.entities.Order;
import com.masprogtech.entities.Payment;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.enums.PayStatus;
import com.masprogtech.enums.PaymentStatus;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.OrderRepository;
import com.masprogtech.repositories.PaymentRepository;
import com.masprogtech.services.PaymentService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    private final RestTemplate restTemplate;


    private static final String WIZA_API_URL = "https://api.wiza.ao/v1/hosts/payments";
    private static final String AUTH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJtZXJjaGFudF9pZCI6IjE0NDhmMGFkLTgxZDUtNDA3Yy1hM2MzLTk1NTVkY2RlMWFkZCIsInRva2VuX2lkIjoiMWI5OWE0MDctZDE4ZC00ZGRjLTkxNjMtMDMzOGJhYWU1NzZjIiwidHlwZSI6InBheW1lbnQiLCJleHAiOjE3NzQ1Njk1NDB9.bQQgUw7iOeqOjKX4UW7Ik4eQnGmYKAOr1gK97ES9rHk";

    public PaymentServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @Override
    public String processPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));

        Optional<Payment> existingPayment = paymentRepository.findByOrder(order);
        if (existingPayment.isPresent()) {
            throw new RuntimeException("Pagamento já iniciado para esta ordem.");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalPrice());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        payment.setReferenceId(order.getOrderId().toString());

        paymentRepository.save(payment);

        // Configuração da requisição para a API da Wiza
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", order.getTotalPrice().toString());
        requestBody.put("currency", "aoa");
        requestBody.put("reference_id", order.getOrderId().toString());
        requestBody.put("customer", order.getCustomer());
        requestBody.put("success_url", "https://where-to-redirect-in-case-of-success");
        requestBody.put("failure_url", "https://where-to-redirect-in-case-of-failure");
        requestBody.put("callback_url", "https://f2b6-102-213-104-208.ngrok-free.app/payments/payment/confirm");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(AUTH_TOKEN);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    WIZA_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            String paymentUrl = getPaymentUrl(response);

            payment.setPaymentUrl(paymentUrl);
            paymentRepository.save(payment);

            return paymentUrl;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar pagamento: " + e.getMessage(), e);
        }
    }


    @Transactional
    @Override
    public void confirmPayment(PaymentConfirmationDTO confirmationDTO) {

        Payment payment = paymentRepository.findByReferenceId(confirmationDTO.getReferenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "referenceId", confirmationDTO.getReferenceId()));

        // Atualiza informações do pagamento
        payment.setStatus(PaymentStatus.valueOf(confirmationDTO.getStatus().toUpperCase()));
        payment.setStatusDateTime(confirmationDTO.getStatusDatetime());
        payment.setStatusReason(confirmationDTO.getStatusReason());
        payment.setCurrency(confirmationDTO.getCurrency());
        payment.setAmount(Double.parseDouble(confirmationDTO.getAmount()));
        payment.setCustomer(confirmationDTO.getCustomer());

        if (payment.getStatus() == PaymentStatus.ACCEPTED) {
            Order order = payment.getOrder();

            // Garante que só muda para SHIPPED se estiver PENDING
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new IllegalStateException("O pedido precisa estar PENDING para ser enviado (SHIPPED)");
            }

            order.setStatus(OrderStatus.SHIPPED);
            order.setPayStatus(PayStatus.PAID); // Define como PAGO
            orderRepository.save(order);
        }

        paymentRepository.save(payment); // Salva as alterações no pagamento
    }




    private String getPaymentUrl(ResponseEntity<String> response) {
        List<String> locationHeaders = response.getHeaders().get("Location");

        if (!locationHeaders.isEmpty()) {
            return locationHeaders.get(0); // Retorna a primeira URL do cabeçalho 'Location'
        }

        throw new RuntimeException("URL de pagamento não encontrada na resposta da API.");
    }






}
