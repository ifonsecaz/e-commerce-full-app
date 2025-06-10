package com.ecommerce.shopplantservice.entity;


import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long product_id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    @Column(nullable = false)
    private double price;
    private String image;
    private int stock_quantity;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private Set<Product_category> categories=new HashSet<>();

    public Product(){
    }

    public Product(String name, String description, String image,  double price, int stock_quantity){
        this.name=name;
        this.description=description;
        this.price=price;
        this.stock_quantity=stock_quantity;
        this.image=image;
    }

    public long getProduct_id(){
        return product_id;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image=image;
    } 

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public Set<Product_category> getCategories(){
        return categories;
    }

    public boolean addCategory(Category category){
        for (Product_category ab : categories) {
            if (ab.getCategory().equals(category)) {
                return false;
            }
        }
        Product_category nuevo=new Product_category();
        nuevo.setProduct(this);
        nuevo.setCategory(category);    
        categories.add(nuevo);
        category.addRelProduct(nuevo);
        return true;
    }

    public void removeRelCategory(Product_category nuevo){
        categories.remove(nuevo);
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public double getPrice(){
        return price;
    } 

    public void setPrice(double price){
        this.price=price;
    }

    public int getStock_quantity(){
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity){
        this.stock_quantity=stock_quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return product_id == product.product_id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(product_id);
    }
}