package com.masprogtech.controllers;


import com.masprogtech.dtos.PaymentConfirmationDTO;
import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.repositories.OrderRepository;
import com.masprogtech.repositories.PaymentRepository;
import com.masprogtech.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Tag(name = "Pagamento", description = "Endpoints para gerenciar Pagamentos" )
public class PaymentController {

   private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;

    }

    @Operation(summary = "Processar pagamento", description = "Processar pagamento",

            responses = {
                    @ApiResponse(responseCode = "201", description = "pagamento processado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
            })
    @PostMapping("/process/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable Long orderId) {
        String paymentUrl = paymentService.processPayment(orderId);
        return ResponseEntity.ok(paymentUrl);
    }


    @Operation(summary = "Confirmar Pagamento", description = "Confirmar pagamento",

            responses = {
                    @ApiResponse(responseCode = "201", description = "pagamento confirmado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentConfirmationDTO.class)))
            })
    @PostMapping("/payment/confirm")
    public ResponseEntity<String> confirmPayment(@RequestBody PaymentConfirmationDTO confirmationDTO) {
        System.out.println("Received callback: " + confirmationDTO);
        paymentService.confirmPayment(confirmationDTO);
        return ResponseEntity.ok("Pagamento confirmado com sucesso.");
    }
}
