package com.masprogtech.services;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.enums.OrderStatus;

public interface OrderService {

    OrderDTO createOrder(OrderDTO order);
    OrderDTO updateOrderStatus(Long orderId, OrderStatus status);
}
