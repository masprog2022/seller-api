package com.masprogtech.repositories;

import com.masprogtech.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    long count();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.payStatus = 'PAID'")
    Double sumTotalPriceByPayStatusPaid();

}
