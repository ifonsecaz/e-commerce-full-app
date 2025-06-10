package com.ecommerce.gatewayservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecommerce.gatewayservice.entity.*;
import com.ecommerce.gatewayservice.repository.TokenvalidRepository;
import com.ecommerce.gatewayservice.security.JwtUtil;
import com.ecommerce.gatewayservice.security.RateLimiterService;

import io.github.bucket4j.Bucket;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private RateLimiterService rateLimiterService;
    @Autowired
    private JwtUtil jwtUtils;
    @Autowired
    private TokenvalidRepository tokenvalidRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SimpleUserDTO ruser, HttpServletRequest request, HttpServletResponse response) {
        String ip = request.getRemoteAddr();
        Bucket bucket = rateLimiterService.resolveBucket(ip,"login");
        if (bucket.tryConsume(1)) {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SimpleUserDTO> entity = new HttpEntity<>(ruser,headers);

            String uri = "http://localhost:8081/auth/login";
             
            try {
                ResponseEntity<String> response2 = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
                if(response2.getStatusCode()== HttpStatus.OK){
                    ResponseCookie jwtCookie = ResponseCookie.from("jwt", response2.getBody())
                        .httpOnly(true)
                        .secure(false) // set to true in production (HTTPS only)
                        .path("/")
                        .maxAge(24 * 60 * 60) // 1 day
                        .sameSite("Strict")   // or "Lax"
                        .build();
                        
                    response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
            System.out.println(jwtUtils.getUserIdFromToken(response2.getBody()));

                    return ResponseEntity.ok("Login successful");
                }
                else{
                    return ResponseEntity.status(response2.getStatusCode()).body(response2.getBody());
                }

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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDTO> entity = new HttpEntity<>(user,headers);

        String uri = "http://localhost:8081/auth/register";

        try {
            ResponseEntity<String> response2 = restTemplate.exchange(
                uri, HttpMethod.POST, entity, String.class
            );
            String token=response2.getBody();
            Tokenvalid newU=new Tokenvalid(jwtUtils.getUserIdFromToken(token), LocalDateTime.now().minusMinutes(1));
            tokenvalidRepository.save(newU);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", response2.getBody())
                        .httpOnly(true)
                        .secure(false) // set to true in production (HTTPS only)
                        .path("/")
                        .maxAge(24 * 60 * 60) // 1 day
                        .sameSite("Strict")   // or "Lax"
                        .build();
                        
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
            System.out.println(jwtUtils.getUserIdFromToken(token));
            return ResponseEntity.ok("Registered successfully");

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

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserDTO user, HttpServletResponse response) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserDTO> entity = new HttpEntity<>(user,headers);

        String uri = "http://localhost:8081/auth/register/admin";
         
        try {
            ResponseEntity<String> response2 = restTemplate.exchange(
                uri, HttpMethod.POST, entity, String.class
            );
            String token=response2.getBody();
            Tokenvalid newU=new Tokenvalid(jwtUtils.getUserIdFromToken(token), LocalDateTime.now().minusMinutes(1));
            tokenvalidRepository.save(newU);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", response2.getBody())
                        .httpOnly(true)
                        .secure(false) // set to true in production (HTTPS only)
                        .path("/")
                        .maxAge(24 * 60 * 60) // 1 day
                        .sameSite("Strict")   // or "Lax"
                        .build();
                        
            response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

            return ResponseEntity.ok("Registered successfully");

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
}

