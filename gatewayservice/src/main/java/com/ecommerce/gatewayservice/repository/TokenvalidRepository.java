package com.ecommerce.gatewayservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.gatewayservice.entity.Tokenvalid;

public interface  TokenvalidRepository extends JpaRepository<Tokenvalid, Long> {
    
}
