package com.ecommerce.shopplantservice.entity;

public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private String image;
    private Double price;
    private Integer stockQuantity;

    // Constructors
    public ProductDTO() {
    }

    public ProductDTO(Long productId, String name, String description, String image, 
                     Double price, Integer stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

}