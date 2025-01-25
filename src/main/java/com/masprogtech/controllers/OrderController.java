package com.masprogtech.controllers;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "Endpoints para gerenciar pedidos" )
public class OrderController {


    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Efectuar pedido", description = "Efectuar Produto",

            responses = {
                    @ApiResponse(responseCode = "201", description = "Pedido efectuado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class)))
            })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDto) {
        OrderDTO createdOrder = orderService.createOrder(orderDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar o Status do Pedido", description = "Atualizar o Status do Pedido",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Status actualizado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class)))
            })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

}
