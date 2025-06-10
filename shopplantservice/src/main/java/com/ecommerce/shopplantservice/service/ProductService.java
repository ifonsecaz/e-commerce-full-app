package com.ecommerce.shopplantservice.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ecommerce.shopplantservice.entity.*;
import com.ecommerce.shopplantservice.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService{
    @Autowired 
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    
    //Save
    public CategoryDTO saveCategory(Category category){
        categoryRepository.save(category);
        return new CategoryDTO(category.getCategory_id(), category.getCategory());
    }
    
    @Transactional
    public ProductCategoryDTO saveProduct(Product product, Long category_id){
        Optional<Category> aux =categoryRepository.findById(category_id);
        if(aux.isPresent()&&product!=null){
            Category category=aux.get();
            productRepository.save(product);
            product.addCategory(category);
            ProductCategoryDTO res=new ProductCategoryDTO(category_id, category.getCategory(), product.getProduct_id(), product.getName(), product.getDescription(), product.getImage(), product.getPrice(), product.getStock_quantity());
            return res;
        }
        return null;
    }

    //add cat
    @Transactional
    public ProductCategoryDTO saveProduct(Long product_id, Long category_id){
        Optional<Category> aux =categoryRepository.findById(category_id);
        Optional<Product> aux2=productRepository.findById(product_id);
        if(aux.isPresent()&&aux2.isPresent()){
            Category category=aux.get();
            Product product=aux2.get();
            product.addCategory(category);
            productRepository.save(product);
            ProductCategoryDTO res=new ProductCategoryDTO(category_id, category.getCategory(), product.getProduct_id(), product.getName(), product.getDescription(), product.getImage(), product.getPrice(), product.getStock_quantity());
            return res;
        }
        return null;
    }

    //Read  
    public List<ProductCategoryDTO> fetchProductList(){
        return productRepository.findAllP();
    }

    public List<CategoryDTO> fetchCategoryList(){
        return categoryRepository.findAllCat();
    }

    //Read 1
    public ProductDTO fetchProductByID(Long productID){
        Optional<Product> res= productRepository.findById(productID);
        if(res.isPresent()){
            Product answer=res.get();
            ProductDTO newP=new ProductDTO(answer.getProduct_id(), answer.getName(), answer.getDescription(), answer.getImage(), answer.getPrice(), answer.getStock_quantity());
            return newP;
        }
        return null;
    }

    public List<ProductCategoryDTO> fetchProductByName(String name){
        return productRepository.findByProductNameContaining(name);
    }

    public List<ProductCategoryDTO> fetchProductByCategory(String category){
        return productRepository.findByCategory(category);
    }

    public List<ProductCategoryDTO> fetchProductCategoriesById(Long product_id){
        return productRepository.findPById(product_id);
    }

    //Delete
    public boolean deleteProduct(Long productID){
        boolean res=false;
        Optional<Product> aux= productRepository.findById(productID);
        if(aux.isPresent()){
            Product product=aux.get();
            Set<Product_category> catList=product.getCategories();
            Iterator<Product_category> it=catList.iterator();
            while(it.hasNext()){
                it.next().getCategory().removeProduct(productID);
            }
            productRepository.delete(product);
            res=true;
        }
        return res;
    }


    //Update
    public ProductDTO updateProduct(Product product, Long productID){
        if(productRepository.findById(productID).isPresent()){
            Product aux=productRepository.findById(productID).get();

            if(!aux.getName().equals(product.getName()))
                aux.setName(product.getName());
            if(!aux.getDescription().equals(product.getDescription()))
                aux.setDescription(product.getDescription());
            if(aux.getPrice()!=product.getPrice())
                aux.setPrice(product.getPrice());
            if(aux.getStock_quantity()!=product.getStock_quantity())
                aux.setStock_quantity(product.getStock_quantity());
            if(aux.getImage().equals(product.getImage()))
                aux.setImage(product.getImage());
            productRepository.save(aux);
            return new ProductDTO(aux.getProduct_id(), aux.getName(), aux.getDescription(), aux.getImage(), aux.getPrice(), aux.getStock_quantity());
        }
        return null;
    }

    public ProductDTO updateRefillStock(Long productID, int units){
        if(productRepository.findById(productID).isPresent()){
            Product aux=productRepository.findById(productID).get();
            
            aux.setStock_quantity(aux.getStock_quantity()+units);
                        
            productRepository.save(aux);
            return new ProductDTO(aux.getProduct_id(), aux.getName(), aux.getDescription(), aux.getImage(), aux.getPrice(), aux.getStock_quantity());
        }
        return null;
    }

    public ResponseEntity<?> updateRemoveStock(Long productID, int units){
        if(productRepository.findById(productID).isPresent()){
            Product aux=productRepository.findById(productID).get();
            
            if(aux.getStock_quantity()>=units){
                aux.setStock_quantity(aux.getStock_quantity()-units);
                productRepository.save(aux);
                
                return ResponseEntity.status(HttpStatus.OK).body(new ProductDTO(aux.getProduct_id(), aux.getName(), aux.getDescription(), aux.getImage(), aux.getPrice(), aux.getStock_quantity()));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is not enough units");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    public ProductDTO updatePrice(Long productID, double price){
        if(productRepository.findById(productID).isPresent()){
            Product aux=productRepository.findById(productID).get();
            
            aux.setPrice(price);
            productRepository.save(aux);
            return new ProductDTO(aux.getProduct_id(), aux.getName(), aux.getDescription(), aux.getImage(), aux.getPrice(), aux.getStock_quantity());
        }
        return null;
    }

}