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
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long category_id;
    @Column(nullable = false)
    private String category;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private Set<Product_category> products=new HashSet<>();

    public Category(){
    }

    public Category(String category){
        this.category=category;
    }

    public long getCategory_id(){
        return category_id;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public Set<Product_category> getProducts(){
        return products;
    }

    public void addRelProduct(Product_category nuevo){
        products.add(nuevo);
    }

    public boolean removeProduct(Long product_id){
        boolean res=false;
        Product_category aux=null;
        Product product=null;
        for (Product_category ab : products) {
            if (ab.getProduct().getProduct_id()==product_id) {
                res=true;
                aux=ab;
                product=ab.getProduct();
                break;
            }
        }
        if(aux!=null&&product!=null){
            products.remove(aux);
            product.removeRelCategory(aux);
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return category_id == category.category_id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(category_id);
    }
}