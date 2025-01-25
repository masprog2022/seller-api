package com.masprogtech.services.impl;

import com.masprogtech.dtos.OrderDTO;
import com.masprogtech.dtos.OrderItemDTO;
import com.masprogtech.entities.Order;
import com.masprogtech.entities.OrderItem;
import com.masprogtech.entities.Product;
import com.masprogtech.enums.OrderStatus;
import com.masprogtech.exceptions.ResourceNotFoundException;
import com.masprogtech.repositories.OrderRepository;
import com.masprogtech.repositories.ProductRepository;
import com.masprogtech.services.OrderService;
import com.masprogtech.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }


    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDto) {
        Order order = new Order();
        order.setCustomer(orderDto.getCustomer());
        order.setAddress(orderDto.getAddress());
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = new ArrayList<>();
        double totalPrice = 0.0;

        for (OrderItemDTO itemDto : orderDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "id", itemDto.getProductId()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPrice(product.getSpecialPrice()); // Preço atual do produto
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

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        return mapToDto(updatedOrder);
    }

    private OrderDTO mapToDto(Order order) {

        OrderDTO orderDto = new OrderDTO();
        orderDto.setOrderId(order.getOrderId());
        orderDto.setCustomer(order.getCustomer());
        orderDto.setAddress(order.getAddress());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setStatus(order.getStatus().name());

        List<OrderItemDTO> itemsDto = order.getItems().stream().map(item -> {
            OrderItemDTO itemDto = new OrderItemDTO();
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            return itemDto;
        }).collect(Collectors.toList());

        orderDto.setItems(itemsDto);

        return orderDto;
    }
}
