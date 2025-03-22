package com.masprogtech.services.impl;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.OrderDetailsDTO;
import com.masprogtech.dtos.OrderItemDTO;
import com.masprogtech.dtos.OrderItemDetailDTO;
import com.masprogtech.entities.Order;
import com.masprogtech.entities.OrderItem;
import com.masprogtech.entities.Product;
import com.masprogtech.entities.User;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.enums.PayStatus;
import com.masprogtech.enums.PaymentMode;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.repositories.OrderRepository;
import com.masprogtech.repositories.ProductRepository;
import com.masprogtech.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;




    public OrderServiceImpl(ProductRepository productRepository,
                            OrderRepository orderRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;

    }

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDto, User client) {
        Order order = new Order();
        order.setClient(client); // Usa o User autenticado
        order.setAddress(orderDto.getAddress());
        order.setStatus(OrderStatus.PENDING);
        order.setPayStatus(PayStatus.UNPAID);
        order.setPaymentMode(PaymentMode.valueOf(orderDto.getPaymentMode().toUpperCase()));
        order.setCreatedAt(LocalDateTime.now()); // Adiciona a data de criação

        List<OrderItem> items = new ArrayList<>();
        double totalPrice = 0.0;

        for (OrderItemDTO itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemDto.getProductId()));

            if (product.getQuantity() < itemDto.getQuantity()) {
                throw new IllegalStateException("Product " + product.getName() + " not enough stock.");
            }

            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
            product.setReservedQuantity(product.getReservedQuantity() + itemDto.getQuantity());
            productRepository.save(product);

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(product.getSpecialPrice());
            item.setOrder(order);

            items.add(item);
            totalPrice += item.getPrice() * item.getQuantity();
        }

        order.setItems(items);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);


        return mapToDto(savedOrder);
    }



    @Transactional
    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        // Se for CANCELADO, devolve a quantidade ao estoque
        if (status == OrderStatus.CANCELLED) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                product.setReservedQuantity(product.getReservedQuantity() - item.getQuantity());
                productRepository.save(product);
            }
        }
        // Se o pedido foi enviado (SHIPPED), libera a quantidade reservada
        else if (status == OrderStatus.SHIPPED) {
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new IllegalStateException("Pedido precisa estar PENDING antes de ser SHIPPED");
            }
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                product.setReservedQuantity(product.getReservedQuantity() - item.getQuantity());
                productRepository.save(product);
            }
            order.setPayStatus(PayStatus.UNPAID);
        }
        // Se o pedido foi entregue (DELIVERED), apenas confirma a entrega
        else if (status == OrderStatus.DELIVERED) {
            if (order.getStatus() != OrderStatus.SHIPPED) {
                throw new IllegalStateException("Pedido precisa estar SHIPPED antes de ser DELIVERED");
            }
            order.setPayStatus(PayStatus.PAID);
        }

        // Atualiza o status
        order.setStatus(status);
        order.setUpdatedAt(LocalDateTime.now());
        Order updatedOrder = orderRepository.save(order);

             return mapToDto(updatedOrder);
    }


    @Transactional
    @Override
    public OrderDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        return modelMapper.map(order, OrderDTO.class);
    }
    @Transactional
    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MessageResponse deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

       orderRepository.delete(order);
        return new MessageResponse("Order successfully removed", true);
    }

    @Transactional
    @Override
    public OrderDetailsDTO getOrderDetailsId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
        orderDetailsDTO.setOrderId(order.getOrderId());
        orderDetailsDTO.setClientName(order.getClient().getName()); // Usa o nome do User
        orderDetailsDTO.setClientId(order.getClient().getUserId());
        orderDetailsDTO.setClientTelephone(order.getClient().getTelephone());
        orderDetailsDTO.setAddress(order.getAddress());
        orderDetailsDTO.setStatus(order.getStatus().toString());
        orderDetailsDTO.setPayStatus(order.getPayStatus().toString());
        orderDetailsDTO.setPaymentMode(order.getPaymentMode().toString());
        orderDetailsDTO.setTotalPrice(order.getTotalPrice());
        orderDetailsDTO.setCreatedAt(order.getCreatedAt());

        List<OrderItemDetailDTO> items = order.getItems().stream().map(item -> {
            OrderItemDetailDTO itemDTO = new OrderItemDetailDTO();
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).toList();

        orderDetailsDTO.setItems(items);

        return orderDetailsDTO;
    }

    private OrderItemDTO mapToItemDto(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        return dto;
    }

    private OrderDTO mapToDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setOrderId(order.getOrderId());
        dto.setClientId(order.getClient().getUserId());
        dto.setItems(order.getItems().stream().map(this::mapToItemDto).collect(Collectors.toList()));
        dto.setTotalPrice(order.getTotalPrice());
        dto.setAddress(order.getAddress());
        dto.setStatus(order.getStatus().name());
        dto.setPayStatus(order.getPayStatus().name());
        dto.setPaymentMode(order.getPaymentMode().name());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        return dto;
    }
}
