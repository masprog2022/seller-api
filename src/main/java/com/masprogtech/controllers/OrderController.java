package com.masprogtech.controllers;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.OrderDetailsDTO;
import com.masprogtech.dtos.ProductDTO;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.payload.MessageResponse;
import com.masprogtech.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Pedido", description = "Endpoints para gerenciar pedidos" )
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

    @Operation(summary = "Listar pedido pelo identificador", description = "Listar pedido pelo identificador",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido listado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class)))
            })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Listar todos pedidos", description = "Listar todos pedidos",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos listado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class)))
            })
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Detalhes do pedido", description = "Detalhe do pedido",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Detalhe do pedido com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDetailsDTO.class)))
            })
    @GetMapping("/details/{orderId}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetailsById(@PathVariable Long orderId) {
        OrderDetailsDTO orderDetailsDTO = orderService.getOrderDetailsId(orderId);
        return ResponseEntity.ok(orderDetailsDTO);
    }


    @Operation(summary = "Remover Pedido", description = "Remover Pedido",

            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido removido com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class)))
            })
    @DeleteMapping("/{orderId}")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long orderId) {

        MessageResponse messageResponse = orderService.deleteOrder(orderId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(messageResponse);
    }
}
