package com.masprogtech.dtos;


import java.time.LocalDateTime;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;

    private Boolean hasOrders;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name,  String description, Boolean isActive, Boolean hasOrders, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.hasOrders = hasOrders;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public CategoryDTO(String name,  String description) {
        this.name = name;
        this.description = description;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasOrders() {
        return hasOrders;
    }

    public void setHasOrders(Boolean hasOrders) {
        this.hasOrders = hasOrders;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
