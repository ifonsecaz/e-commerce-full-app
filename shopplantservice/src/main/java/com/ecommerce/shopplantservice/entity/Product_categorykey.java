package com.ecommerce.shopplantservice.entity;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class Product_categorykey {
    private Long product_id;
    private Long category_id;

    public Product_categorykey(){

    }

    public Product_categorykey(Long product_id, Long category_id){
        this.product_id=product_id;
        this.category_id=category_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product_categorykey)) return false;
        Product_categorykey that = (Product_categorykey) o;
        return Objects.equals(product_id, that.product_id) &&
               Objects.equals(category_id, that.category_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, category_id);
    }
}
