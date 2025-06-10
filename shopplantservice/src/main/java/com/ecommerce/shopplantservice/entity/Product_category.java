package com.ecommerce.shopplantservice.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Product_category {
    @EmbeddedId
    Product_categorykey id;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    Product product; 

    @ManyToOne
    @MapsId("category_id")
    @JoinColumn(name = "category_id")
    Category category;

    public Product_category() {
    }

    public Product_category(Product product, Category category) {
        this.product = product;
        this.category = category;
        this.id = new Product_categorykey(product.getProduct_id(), category.getCategory_id());
    }

    public Product_category(Product_categorykey key, Product product, Category category) {
        this.product = product;
        this.category = category;
        this.id = key;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product_category that = (Product_category) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
