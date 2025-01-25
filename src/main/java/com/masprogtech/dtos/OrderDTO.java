package com.masprogtech.dtos;

import java.util.List;

public class OrderDTO {

    private Long orderId;
    private String customer;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private String address;
    private String status;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String customer, List<OrderItemDTO> items,
                    Double totalPrice, String address, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
