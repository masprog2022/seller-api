package com.masprogtech.services;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.OrderDetailsDTO;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.payload.MessageResponse;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO order);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus status);
    OrderDTO getOrderById(Long orderId);
    List<OrderDTO> getAllOrders();
    MessageResponse deleteOrder(Long productId);

    OrderDetailsDTO getOrderDetailsId(Long orderId);

}
