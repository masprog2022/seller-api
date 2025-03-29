package com.masprogtech.dtos;

public class OrderStatusStatsDTO {

    private String status;
    private long orders;

    public OrderStatusStatsDTO(String status, long orders) {
        this.status = status;
        this.orders = orders;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOrders() {
        return orders;
    }

    public void setOrders(long orders) {
        this.orders = orders;
    }
}
