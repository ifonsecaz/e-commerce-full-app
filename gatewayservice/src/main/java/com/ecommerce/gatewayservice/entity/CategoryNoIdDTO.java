package com.ecommerce.gatewayservice.entity;

public class CategoryNoIdDTO {
    private String category;

    public CategoryNoIdDTO() {
    }

    public CategoryNoIdDTO(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                ", category='" + category + '\'' +
                '}';
    }
}