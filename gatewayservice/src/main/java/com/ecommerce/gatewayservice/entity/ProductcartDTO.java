package com.ecommerce.gatewayservice.entity;

public class ProductcartDTO {
    public long user_id;
    public long product_id;
    public int quantity;
    public double unit_price;
    public String image;
    public String name;

    public ProductcartDTO(){
        
    }

    public ProductcartDTO(long user_id, long product_id, int quantity, double unit_price, String image, String name){
        this.user_id=user_id;
        this.product_id=product_id;
        this.quantity=quantity;
        this.unit_price=unit_price;
        this.image=image;
        this.name=name;
    }

    public ProductcartDTO(long product_id, int quantity){
        this.product_id=product_id;
        this.quantity=quantity;
    }
}
