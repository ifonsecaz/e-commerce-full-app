package com.ecommerce.shopplantservice.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.ecommerce.shopplantservice.entity.*;
import com.ecommerce.shopplantservice.service.*;


@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductService productService;
    
    @PostMapping("/add-category")
    public ResponseEntity<?> saveCategory(
        @Valid @RequestBody Category category)
    {
        CategoryDTO res=productService.saveCategory(category);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @PostMapping("/add/{category_id}")
    public ResponseEntity<?> saveProduct(
        @Valid @RequestBody Product product,
        @PathVariable Long category_id)
    {
        ProductCategoryDTO res=productService.saveProduct(product,category_id);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Verify the data");
        }
    }

    @PostMapping("/add-prod/{product_id}/category/{category_id}")
    public ResponseEntity<?> saveProduct(
        @PathVariable Long product_id,
        @PathVariable Long category_id)
    {
        ProductCategoryDTO res=productService.saveProduct(product_id,category_id);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("The provided ID couldn't be found");
        }
    }


    @GetMapping("/productlist")
    public List<ProductCategoryDTO> fetchProductList()
    {
        return productService.fetchProductList();
    }

    @GetMapping("/categorylist")
    public List<CategoryDTO> fetchCategoryList()
    {
        return productService.fetchCategoryList();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id") Long productID)
    {
        ProductDTO res= productService.fetchProductByID(productID);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @GetMapping("/product-categories/{id}")
    public List<ProductCategoryDTO> fetchProductCatById(@PathVariable("id") Long productID)
    {
        return productService.fetchProductCategoriesById(productID);
    }

    @GetMapping("/product/name/{name}")
    public List<ProductCategoryDTO> fetchProductName(@PathVariable("name") String name)
    {
        return productService.fetchProductByName(name);
    }

    
    @GetMapping("/product/category/{category}")
    public List<ProductCategoryDTO> fetchProductCategory(@PathVariable("category") String category)
    {
        return productService.fetchProductByCategory(category);
    }

    @GetMapping("/product/{id}/units")
    public int fetchProductUnits(@PathVariable("id") long product_id)
    {
        ProductDTO aux= productService.fetchProductByID(product_id);
        int res=0;
        if(aux!=null)
            res=aux.getStockQuantity();
        return res;
    }

    @GetMapping("/product/{id}/price")
    public double fetchProductPrice(@PathVariable("id") long product_id)
    {
        ProductDTO aux= productService.fetchProductByID(product_id);
        double res=0;
        if(aux!=null)
            res=aux.getPrice();
        return res;
    }
    
 
    @PutMapping("/update/product/{id}")
    public ResponseEntity<?>
    updateProduct(@Valid @RequestBody Product product,
                     @PathVariable("id") Long productId)
    {
        ProductDTO res= productService.updateProduct(product, productId);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
    
    @PutMapping("/update/product/{id}/price/{price}")
    public ResponseEntity<?>
    updateProductPrice(@PathVariable("id") Long productId,
                     @PathVariable("price") double price)
    {
        if(price<1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The product price can't be lower than $1");
        }
        ProductDTO res= productService.updatePrice(productId, price);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    //call to create update order
    @PutMapping("/update/product/{id}/unitsbuy/{units}")
    public ResponseEntity<?>
    updateProductUnits(@PathVariable("id") Long productId,
                        @PathVariable("units") int units)
    {
        if(units<1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Units buyed can't be negative");
        }
        return productService.updateRemoveStock(productId, units);
    }

    //called when removing object
    @PutMapping("/update/product/{id}/refill/{units}")
    public ResponseEntity<?>
    updateProductRefill(@PathVariable("id") Long productId,
                        @PathVariable("units") int units)
    {
        if(units<1){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Units buyed can't be negative");
        }
        ProductDTO res= productService.updateRefillStock(productId, units);
        if(res!=null){
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id")
                                       Long productId)
    {
        if(productService.deleteProduct(productId))
            return ResponseEntity.status(HttpStatus.OK).body("Deleted succesfully");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The provided ID couldnt be found");
    }
}