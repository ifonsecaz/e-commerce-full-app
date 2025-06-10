package com.ecommerce.gatewayservice.controller;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.gatewayservice.entity.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.net.URI;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        RestTemplate restTemplate = new RestTemplate();
        
        String uri = "http://localhost:8081/admin/delete/" + id;
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // This captures 4xx and 5xx errors and returns them as-is
            return ResponseEntity
                    .status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());

        } catch (Exception ex) {
            // Fallback for unexpected issues (e.g., timeout, no connection)
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        final String uri = "http://localhost:8082/products/delete/" + id;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(uri, HttpMethod.DELETE,null, String.class);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/products/refill/{id}/units/{units}")
    public ResponseEntity<?> refillProduct(@PathVariable Long id,@PathVariable int units) {
        final String uri = "http://localhost:8082/products/update/product/"+id+"/refill/" + units;
        RestTemplate restTemplate = new RestTemplate();
        
        try{
            return restTemplate.exchange(uri, HttpMethod.PUT, null, ProductDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/products/product/{id}/price/{price}")
    public ResponseEntity<?> changePrice(@PathVariable Long id,@PathVariable double price) {
        final String uri = "http://localhost:8082/products/update/product/"+id+"/price/"+price;
        RestTemplate restTemplate = new RestTemplate();
        try{
            return restTemplate.exchange(uri, HttpMethod.PUT, null, ProductDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
        
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> refillProduct(@PathVariable Long id,@RequestBody ProductNoIdDTO product) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8082/products/update/product/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductNoIdDTO> entity = new HttpEntity<>(product,headers);

        try{
            return restTemplate.exchange(uri,HttpMethod.PUT, entity, ProductDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }
///
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/products/add/{category_id}")
    public ResponseEntity<?> addProduct(@RequestBody ProductNoIdDTO product, @PathVariable Long category_id) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8082/products/add/"+category_id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductNoIdDTO> entity = new HttpEntity<>(product,headers);

        try{
            return restTemplate.postForEntity(uri, entity, ProductCategoryDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/products/add-category")
    public ResponseEntity<?> addProduct(@RequestBody CategoryNoIdDTO category) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8082/products/add-category";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CategoryNoIdDTO> entity = new HttpEntity<>(category,headers);

        try{
            return restTemplate.postForEntity(uri, entity, CategoryDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/products/add-prod/{product_id}/category/{category_id}")
    public ResponseEntity<?> addProduct(@PathVariable Long product_id,
        @PathVariable Long category_id) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8082/products/add-prod/"+product_id+"/category/"+category_id;

        try{
            return restTemplate.exchange(uri, HttpMethod.POST, null, ProductCategoryDTO.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Handle error response as String
            String errorBody = ex.getResponseBodyAsString();
            HttpStatusCode statusCode = ex.getStatusCode();

            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (Exception ex) {
            // Catch all other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
        }
    }
 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/cancel-order/{order_id}")
    public ResponseEntity<?> cancelOrder(@PathVariable("order_id") long order_id) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "http://localhost:8083/orders/cancel/order/"+order_id;
        
        return restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/view-orders")
    public ResponseEntity<?> viewOrderList() {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "http://localhost:8083/orders/orderslist";
        
        ResponseEntity<List<OrdersDTO>> response = restTemplate.exchange(uri,HttpMethod.GET,null,new ParameterizedTypeReference<List<OrdersDTO>>() {});

        return response;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/view-order-details/{order_id}")
    public ResponseEntity<?> viewOrderDetails(@PathVariable("order_id") long order_id) {
        RestTemplate restTemplate = new RestTemplate();

        String uri = "http://localhost:8083/orders/ordersdetails/"+order_id;
        
        ResponseEntity<List<OrderdetailsDTO>> response = restTemplate.exchange(uri,HttpMethod.GET,null,new ParameterizedTypeReference<List<OrderdetailsDTO>>() {});

        return response;
    }

}
