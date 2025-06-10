package com.ecommerce.gatewayservice.entity;

public class OrderdetailsDTO {
    private long order_detail_id;
    private long product_id;
    private int quantity;
    private double unit_price;
    private String image;
    private String name;

    public OrderdetailsDTO(){

    }

    public OrderdetailsDTO(long product_id, int quantity, double unit_price, String name, String image){
        this.product_id=product_id;
        this.quantity=quantity;
        this.unit_price=unit_price;
        this.name=name;
        this.image=image;
    }

    public long getOrder_detail_id(){
        return order_detail_id;
    }

    public long getProduct_id(){
        return product_id;
    }

    public String getName(){
        return name;
    }

    public String getImage(){
        return image;
    }


   
    public int getQuantity(){
        return quantity;
    }

    public double getUnit_price(){
        return unit_price;
    }
}
