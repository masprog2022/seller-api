package com.masprogtech.entities;

import com.masprogtech.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String Customer;

    @OneToMany(mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<OrderItem> items  = new ArrayList<>();

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String address;

    public Order() {
    }

    public Order(Long orderId, String customer, List<OrderItem> items,
                 Double totalPrice, OrderStatus status, String address) {
        this.orderId = orderId;
        Customer = customer;
        this.items = items;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String customer) {
        Customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
