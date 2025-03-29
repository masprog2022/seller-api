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
        CASE 
            WHEN EXTRACT(MONTH FROM o.createdAt) = 1 THEN 'Janeiro'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 2 THEN 'Fevereiro'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 3 THEN 'Março'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 4 THEN 'Abril'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 5 THEN 'Maio'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 6 THEN 'Junho'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 7 THEN 'Julho'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 8 THEN 'Agosto'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 9 THEN 'Setembro'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 10 THEN 'Outubro'
            WHEN EXTRACT(MONTH FROM o.createdAt) = 11 THEN 'Novembro'
            ELSE 'Dezembro'
        END,
        SUM(CASE WHEN o.status = 'PENDING' THEN 1 ELSE 0 END),
        SUM(CASE WHEN o.status = 'DELIVERED' THEN 1 ELSE 0 END)
    )
    FROM Order o
    GROUP BY EXTRACT(MONTH FROM o.createdAt)
    ORDER BY EXTRACT(MONTH FROM o.createdAt)
""")
    List<OrderReportDTO> countOrdersByMonth();


    @Query("SELECT o.status as status, COUNT(o) as orders FROM Order o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();






}
