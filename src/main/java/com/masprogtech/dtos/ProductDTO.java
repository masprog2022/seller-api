package com.masprogtech.dtos;

import java.time.LocalDateTime;

public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private double price;
    private double discount;
    private double specialPrice;
    private String categoryName;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Integer quantity, double price, double discount,
                      double specialPrice, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
        this.specialPrice = specialPrice;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(double specialPrice) {
        this.specialPrice = specialPrice;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}
