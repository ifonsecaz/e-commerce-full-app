package com.ecommerce.userservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.userservice.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.ecommerce.userservice.entity.User;



@RestController
@RequestMapping("/admin")
public class ApiAdminController {
    private final UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;

    public ApiAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        User aux=userRepository.findById(id).get();
        aux.removeRole();
        userRepository.delete(aux);
        return ResponseEntity.ok("User deleted");
    }
    
}
    