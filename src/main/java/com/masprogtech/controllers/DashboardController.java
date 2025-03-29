package com.masprogtech.controllers;


import com.masprogtech.dtos.DashboardStatsDTO;
import com.masprogtech.dtos.OrderReportDTO;
import com.masprogtech.dtos.OrderStatusStatsDTO;
import com.masprogtech.services.DashboardService;
import com.masprogtech.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "dados para análises" )
public class DashboardController {


    private final DashboardService dashboardService;
    private final OrderService orderService;

    public DashboardController(DashboardService dashboardService, OrderService orderService) {
        this.dashboardService = dashboardService;
        this.orderService = orderService;
    }

    @Operation(summary = "Dashboard", description = "Dashboard" +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name ="security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Dashboard com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DashboardStatsDTO.class))),

            })
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        DashboardStatsDTO stats = dashboardService.getDashboardStats();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Relatório pedidos por mês (pendentes e entregues)", description = "Relatórios para exibir nos gráficos"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "relatórios exibidos com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderReportDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @GetMapping("/report")
    public List<OrderReportDTO> getOrderReport() {
        return orderService.getOrdersByMonth();
    }

    @Operation(summary = "Relatório pedidos por status", description = "Relatórios para exibir nos gráficos"+
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "relatórios exibidos com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderReportDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado (não é Admin)")
            })
    @GetMapping("/by-status")
    public ResponseEntity<List<OrderStatusStatsDTO>> getOrdersByStatus() {
        List<OrderStatusStatsDTO> stats = orderService.getOrderStatsByStatus();
        return ResponseEntity.ok(stats);
    }


}
