package com.ecommerce.shopplantservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecommerce.shopplantservice.entity.Category;
import com.ecommerce.shopplantservice.entity.CategoryDTO;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    @Query("SELECT new com.ecommerce.shopplantservice.entity.CategoryDTO(" + 
    "b.category_id, b.category)" +
    "FROM Category b")
    List<CategoryDTO> findAllCat();
}
