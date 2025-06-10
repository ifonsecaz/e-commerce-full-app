package com.ecommerce.gatewayservice.entity;

public class ProductNoIdDTO {
    private String name;
    private String category;
    private String description;
    private double price;
    private int stock_quantity;
    public String image;
    
    public ProductNoIdDTO(String name, String category, String description, double price, int stock_quantity, String image){
        this.name=name;
        this.category=category;
        this.description=description;
        this.price=price;
        this.stock_quantity=stock_quantity;
        this.image=image;
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public String getDescription(){
        return description;
    }

    public double getPrice(){
        return price;
    } 

    public int getStock_quantity(){
        return stock_quantity;
    }

}