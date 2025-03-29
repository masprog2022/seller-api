package com.masprogtech.services;

import com.masprogtech.dtos.DashboardStatsDTO;

import java.util.List;

public interface DashboardService {
    public long getOrderCount();
    public long getCustomerCount();

    DashboardStatsDTO getDashboardStats();

    public double getTotalSales();

    public long getProductCount();


}
