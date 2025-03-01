package com.masprogtech.entities;

import com.masprogtech.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_payments", uniqueConstraints = @UniqueConstraint(columnNames = "reference_id"))
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId", unique = true)
    private Order order;

    @Column(name = "reference_id", unique = true, nullable = false)
    private String referenceId;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String statusReason;
    private LocalDateTime statusDateTime;
    @Column(name = "currency")
    private String currency;
    private LocalDateTime createdAt;
    private String paymentUrl;

    @Column(name = "customer")
    private String customer;




}
