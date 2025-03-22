package com.masprogtech.dtos;

public class DashboardStatsDTO {
    private long orderCount;
    private long customerCount;

    private double totalSales;
    private long productCount;

    public DashboardStatsDTO(long orderCount, long customerCount, double totalSales, long productCount) {
        this.orderCount = orderCount;
        this.customerCount = customerCount;
        this.totalSales = totalSales;
        this.productCount = productCount;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }

    public long getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(long customerCount) {
        this.customerCount = customerCount;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public long getProductCount() {
        return productCount;
    }

    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
