package com.ecommerce.shopplantservice.entity;

public class CategoryDTO {
    private Long categoryId;
    private String category;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, String category) {
        this.categoryId = categoryId;
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
                "categoryId=" + categoryId +
                ", category='" + category + '\'' +
                '}';
    }
}