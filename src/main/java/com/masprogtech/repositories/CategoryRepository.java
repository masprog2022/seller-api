package com.masprogtech.repositories;

import com.masprogtech.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsCategoryByName(String categoryName);


    List<Category> findByIsActiveTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.isActive = false WHERE c.id = :id")
    void softDelete(@Param("id") Long id);
}
