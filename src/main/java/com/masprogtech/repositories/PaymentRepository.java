package com.masprogtech.repositories;

import com.masprogtech.entities.Order;
import com.masprogtech.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByReferenceId(String referenceId);
    Optional<Payment> findByOrder(Order order);

    //public Optional<Payment> findFirstByOrder(Order order);

}
