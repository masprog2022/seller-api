package com.masprogtech.dtos;

public class OrderMessage {
    private String type;
    private OrderDTO order;

    public OrderMessage(String type, OrderDTO order) {
        this.type = type;
        this.order = order;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public OrderDTO getOrder() { return order; }
    public void setOrder(OrderDTO order) { this.order = order; }
}