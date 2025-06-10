package com.ecommerce.orderservice.entity;

public class ProductCartDTO {
    public long user_id;
    public long product_id;
    public int quantity;
    public double unit_price;
    public String image;
    public String name;

    public ProductCartDTO(){
        
    }

    public ProductCartDTO(long user_id, long product_id, int quantity, double unit_price, String image, String name){
        this.user_id=user_id;
        this.product_id=product_id;
        this.quantity=quantity;
        this.unit_price=unit_price;
        this.image=image;
        this.name=name;
    }
}
