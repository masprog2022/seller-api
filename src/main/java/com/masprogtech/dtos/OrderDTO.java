package com.masprogtech.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.masprogtech.entities.Order;
import com.masprogtech.enums.PayStatus;
import com.masprogtech.enums.PaymentMode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {

    private Long orderId;

    private List<OrderItemDTO> items;
    private Double totalPrice;
    private AddressDTO address;
    private String status;

    private String payStatus;

    private Long clientId;

    private String clientName;

    private String clientTelephone;
    private String paymentMode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public OrderDTO() {}

    // 🔹 Construtor com todos os campos
    public OrderDTO(Long orderId, List<OrderItemDTO> items, double totalPrice, AddressDTO address,
                    String status, String payStatus, Long clientId, String clientName,
                    String clientTelephone, String paymentMode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.address = address;
        this.status = status;
        this.payStatus = payStatus;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientTelephone = clientTelephone;
        this.paymentMode = paymentMode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public OrderDTO(Order savedOrder) {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
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

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientTelephone() {
        return clientTelephone;
    }

    public void setClientTelephone(String clientTelephone) {
        this.clientTelephone = clientTelephone;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", items=" + items +
                ", totalPrice=" + totalPrice +
                ", address=" + address +
                ", status='" + status + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientTelephone='" + clientTelephone + '\'' +
                ", paymentMode='" + paymentMode + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
