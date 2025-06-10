package com.ecommerce.gatewayservice.entity;

public class ProductCategoryDTO {
    private Long productId;
    private String name;
    private String description;
    private String image;
    private Double price;
    private Integer stockQuantity;
    
    private Long categoryId;
    private String category;
    
    public ProductCategoryDTO() {
    }
    
    public ProductCategoryDTO(Long productId, String name, String description, String image, 
                            Double price, Integer stockQuantity, Long categoryId, String category) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.category = category;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
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
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
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
        return "ProductCategoryDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", categoryId=" + categoryId +
                ", category='" + category + '\'' +
                '}';
    }
}