package com.masprogtech.repositories;

import com.masprogtech.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndCategoryId(String name, Long categoryId );
}
