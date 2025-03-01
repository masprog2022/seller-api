package com.masprogtech.dtos;

import com.masprogtech.enums.PayStatus;
import com.masprogtech.enums.PaymentMode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long orderId;
    private String customer;
    private List<OrderItemDTO> items;
    private Double totalPrice;
    private String address;
    private String status;

    private String payStatus;

    private String paymentMode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String customer, List<OrderItemDTO> items,
                    Double totalPrice, String address, String status, LocalDateTime createdAt, LocalDateTime updatedAt ) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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


    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
