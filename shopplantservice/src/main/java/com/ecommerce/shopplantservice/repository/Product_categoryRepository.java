package com.ecommerce.shopplantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.shopplantservice.entity.Product_category;
import com.ecommerce.shopplantservice.entity.Product_categorykey;
import org.springframework.stereotype.Repository;


@Repository
public interface Product_categoryRepository extends JpaRepository<Product_category,Product_categorykey> {
    
}
