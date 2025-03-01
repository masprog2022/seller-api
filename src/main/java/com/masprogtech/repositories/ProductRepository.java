package com.masprogtech.repositories;

import com.masprogtech.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByNameAndCategoryId(String name, Long categoryId );

    List<Product> findByIsActiveTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.isActive = false WHERE p.id = :id")
    void softDelete(@Param("id") Long id);
}
