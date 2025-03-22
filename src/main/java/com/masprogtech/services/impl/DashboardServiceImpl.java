package com.masprogtech.services.impl;

import com.masprogtech.dtos.DashboardStatsDTO;
import com.masprogtech.repositories.OrderRepository;
import com.masprogtech.repositories.ProductRepository;
import com.masprogtech.repositories.UserRepository;
import com.masprogtech.services.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public DashboardServiceImpl(OrderRepository orderRepository,
                                UserRepository userRepository,
                                ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public long getOrderCount() {
        return orderRepository.count();
    }

    @Override
    public long getCustomerCount() {
        return userRepository.countByRole("ROLE_CLIENT");
    }

    @Override
    public double getTotalSales() {
        return orderRepository.sumTotalPriceByPayStatusPaid();
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }

    public DashboardStatsDTO getDashboardStats() {
        return new DashboardStatsDTO(getOrderCount(), getCustomerCount(), getTotalSales(), getProductCount());
    }


}
