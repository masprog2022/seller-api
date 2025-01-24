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
}
