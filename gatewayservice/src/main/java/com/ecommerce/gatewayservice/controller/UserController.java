package com.ecommerce.gatewayservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecommerce.gatewayservice.entity.EmailChangeRequestDTO;
import com.ecommerce.gatewayservice.entity.OrderdetailsDTO;
import com.ecommerce.gatewayservice.entity.OrdersDTO;
import com.ecommerce.gatewayservice.entity.PasswordResetRequestDTO;
import com.ecommerce.gatewayservice.entity.ProductcartDTO;
import com.ecommerce.gatewayservice.entity.UserDTO;
import com.ecommerce.gatewayservice.security.JwtUtil;
import com.ecommerce.gatewayservice.security.RateLimiterService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.web.csrf.CsrfToken;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private RateLimiterService rateLimiterService;
    @Autowired
    private JwtUtil jwtUtils;

    private long extractUserIdFromRequest(HttpServletRequest request) {
        System.out.println("Extracting JWT from cookie...");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    System.out.println("JWT found in cookie: " + token);
                    return jwtUtils.getUserIdFromToken(token);
                }
            }
        }
        System.out.println("JWT cookie not found");
        return 0;
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
            String origin = request.getHeader("Origin");
            System.out.println("Request Origin: " + origin);

        final String uri = "http://localhost:8081/user/info/" + username;
        RestTemplate restTemplate = new RestTemplate();
        
        try{
            return restTemplate.exchange(uri, HttpMethod.GET, null, UserDTO.class);
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

    @PostMapping("/resetpwd")
    public ResponseEntity<?> getUserInfo(@RequestBody PasswordResetRequestDTO req, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"resetpwd");

        if (bucket.tryConsume(1)) {
            RestTemplate restTemplate = new RestTemplate();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String uri = "http://localhost:8081/user/resetpwd/"+username;
            
             
            try {
                ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
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
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many attempts");
        }
    }
        
    @PostMapping("/changemail")
    public ResponseEntity<?> getUserInfo(@RequestBody EmailChangeRequestDTO req, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"resetpwd");

        if (bucket.tryConsume(1)) {
            RestTemplate restTemplate = new RestTemplate();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            String uri = "http://localhost:8081/user/changemail/"+username;
 
            try {
                ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, null, String.class);
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
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many attempts");
        }
    }
///
    @PostMapping("/add-to-cart")
        public ResponseEntity<?> addProductToCart(@RequestBody ProductcartDTO product, HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            String uri = "http://localhost:8083/orders/addproduct";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
   
            long user_id = extractUserIdFromRequest(request);

            product.user_id=user_id;
            product.unit_price=0;
            product.image="";

            HttpEntity<ProductcartDTO> entity = new HttpEntity<>(product,headers);

            try{
                return restTemplate.postForEntity(uri, entity, OrdersDTO.class);
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

        @DeleteMapping("/remove-from-cart/product/{product_id}")
        public ResponseEntity<?> removeProductFromCart(@PathVariable("product_id") long productID, HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/remove/user/"+user_id+"/product/"+productID;

            try{
                return restTemplate.exchange(uri, HttpMethod.DELETE, null, OrdersDTO.class);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                String errorBody = ex.getResponseBodyAsString();
                HttpStatusCode statusCode = ex.getStatusCode();

                return ResponseEntity.status(statusCode).body(errorBody);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
            }
        }

        @DeleteMapping("/empty-cart")
        public ResponseEntity<?> emptyCart(HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/empty/"+user_id;

            return restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
        }

        @DeleteMapping("/cancel-order/{order_id}")
        public ResponseEntity<?> cancelOrder(@PathVariable("order_id") long order_id, HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/cancel/user/"+user_id+"/order/"+order_id;
            
            return restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);
        }

        @PutMapping("/confirm-order")
        public ResponseEntity<?> confirmCart(HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/complete-order/"+user_id;

            try{
                return restTemplate.exchange(uri, HttpMethod.PUT, null, OrdersDTO.class);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                String errorBody = ex.getResponseBodyAsString();
                HttpStatusCode statusCode = ex.getStatusCode();

                return ResponseEntity.status(statusCode).body(errorBody);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
            }
        }

        @PutMapping("/reduce-unit/product/{product_id}")
        public ResponseEntity<?> reduceProductCart(@PathVariable("product_id") long product_id,HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/reduce-product/user/"+user_id+"/product/"+product_id;

            try{
                return restTemplate.exchange(uri, HttpMethod.PUT, null, OrdersDTO.class);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                String errorBody = ex.getResponseBodyAsString();
                HttpStatusCode statusCode = ex.getStatusCode();

                return ResponseEntity.status(statusCode).body(errorBody);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
            }
        }

        @PutMapping("/increase-unit/product/{product_id}")
        public ResponseEntity<?> increaseProductCart(@PathVariable("product_id") long product_id, HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/increment-product/user/"+user_id+"/product/"+product_id;

            try{
                return restTemplate.exchange(uri, HttpMethod.PUT, null, OrdersDTO.class);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                String errorBody = ex.getResponseBodyAsString();
                HttpStatusCode statusCode = ex.getStatusCode();

                return ResponseEntity.status(statusCode).body(errorBody);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
            }
        }

        @GetMapping("/view-cart")
        public ResponseEntity<?> viewCart(HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);
            String uri = "http://localhost:8083/orders/view-cart/"+user_id;
            
            try{
                return restTemplate.exchange(uri, HttpMethod.GET, null, OrdersDTO.class);
            } catch (HttpClientErrorException | HttpServerErrorException ex) {
                String errorBody = ex.getResponseBodyAsString();
                HttpStatusCode statusCode = ex.getStatusCode();

                return ResponseEntity.status(statusCode).body(errorBody);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + ex.getMessage());
            }
        }

        @GetMapping("/view-orders")
        public ResponseEntity<?> viewOrderList(HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/orderslist/"+user_id;
            
            ResponseEntity<List<OrdersDTO>> response = restTemplate.exchange(uri,HttpMethod.GET,null,new ParameterizedTypeReference<List<OrdersDTO>>() {});

            return response;
        }

        @GetMapping("/view-order-details/{order_id}")
        public ResponseEntity<?> viewOrderDetails(@PathVariable("order_id") long order_id, HttpServletRequest request) {
            RestTemplate restTemplate = new RestTemplate();
            
            long user_id = extractUserIdFromRequest(request);

            String uri = "http://localhost:8083/orders/ordersdetails/users/"+user_id+"/order/"+order_id;
            
            ResponseEntity<List<OrderdetailsDTO>> response = restTemplate.exchange(uri,HttpMethod.GET,null,new ParameterizedTypeReference<List<OrderdetailsDTO>>() {});

            return response;
        }

        
}

