package com.ecommerce.shopplantservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.shopplantservice.entity.Product;
import com.ecommerce.shopplantservice.entity.ProductCategoryDTO;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
    @Query("SELECT new com.ecommerce.shopplantservice.entity.ProductCategoryDTO(" + 
        "b.category_id, b.category," +
        "a.product_id, a.name, a.description, a.image, a.price, a.stock_quantity)" +
        "FROM Product a JOIN a.categories ab JOIN ab.category b WHERE b.category LIKE %:category% ORDER BY b.category_id")
    List<ProductCategoryDTO> findByCategory(@Param("category") String category);

    @Query("SELECT new com.ecommerce.shopplantservice.entity.ProductCategoryDTO(" + 
        "b.category_id, b.category," +
        "a.product_id, a.name, a.description, a.image, a.price, a.stock_quantity)" +
        "FROM Product a JOIN a.categories ab JOIN ab.category b WHERE a.name LIKE %:product_name% ORDER BY b.category_id")
    List<ProductCategoryDTO> findByProductNameContaining(@Param("product_name") String product_name);

    @Query("SELECT new com.ecommerce.shopplantservice.entity.ProductCategoryDTO(" +
       "b.category_id, b.category," +
       "a.product_id, a.name, a.description, a.image, a.price, a.stock_quantity) " +
       "FROM Product a JOIN a.categories ab JOIN ab.category b ORDER BY b.category_id")
    List<ProductCategoryDTO> findAllP();

    @Query("SELECT new com.ecommerce.shopplantservice.entity.ProductCategoryDTO(" +
       "b.category_id, b.category," +
       "a.product_id, a.name, a.description, a.image, a.price, a.stock_quantity) " +
       "FROM Product a JOIN a.categories ab JOIN ab.category b WHERE a.product_id = :product_id ORDER BY b.category_id")
    List<ProductCategoryDTO> findPById(@Param("product_id") long product_id);
}