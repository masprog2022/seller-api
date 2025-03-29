package com.masprogtech.services;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.OrderDetailsDTO;
import com.masprogtech.dtos.OrderReportDTO;
import com.masprogtech.dtos.OrderStatusStatsDTO;
import com.masprogtech.entities.User;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.payload.MessageResponse;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO order, User client);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus status);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getAllOrders();
    MessageResponse deleteOrder(Long productId);

    OrderDetailsDTO getOrderDetailsId(Long orderId);

    List<OrderReportDTO> getOrdersByMonth();

    List<OrderStatusStatsDTO> getOrderStatsByStatus();

}
