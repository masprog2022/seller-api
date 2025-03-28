package com.masprogtech.repositories;

import com.masprogtech.dtos.OrderReportDTO;
import com.masprogtech.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    long count();

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.payStatus = 'PAID'")
    Double sumTotalPriceByPayStatusPaid();

    @Query("""
    SELECT new com.masprogtech.dtos.OrderReportDTO(
        TRIM(TO_CHAR(o.createdAt, 'Month')),
        SUM(CASE WHEN o.status = 'PENDING' THEN 1 ELSE 0 END),
        SUM(CASE WHEN o.status = 'DELIVERED' THEN 1 ELSE 0 END)
    )
    FROM Order o
    GROUP BY TO_CHAR(o.createdAt, 'Month'), EXTRACT(MONTH FROM o.createdAt)
    ORDER BY EXTRACT(MONTH FROM o.createdAt)
""")
    List<OrderReportDTO> countOrdersByMonth();


}
