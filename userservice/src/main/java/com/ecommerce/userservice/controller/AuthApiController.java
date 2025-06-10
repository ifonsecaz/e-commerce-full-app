package com.ecommerce.userservice.controller;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.userservice.entity.*;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.JwtUtil;
import com.ecommerce.userservice.service.RoleService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/auth")
public class AuthApiController {
    private final AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
    @Autowired
    private RoleService roleService;

    public AuthApiController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody SimpleUser ruser, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            ruser.getUsername(),
                            ruser.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user = userRepository.findByUsername(ruser.getUsername());
        
        String token = jwtUtils.generateToken(user); //gateway service

        return ResponseEntity.ok(token);
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
        }
        if (user.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password too short");
        }
        // Create new user's account
        user.setPassword(encoder.encode(user.getPassword()));
        Role aux= roleService.verifyRole("USER");
        if(aux!=null){
            userRepository.save(user);
            user.setRole(aux);
        }

        String token = jwtUtils.generateToken(user); //gateway service
        return ResponseEntity.ok(token);
    }

    @Transactional
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid User user) {
        if (userRepository.findByUsername(user.getUsername())!=null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Username is already taken!");
        }
        if (user.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password too short");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Role aux= roleService.verifyRole("ADMIN");
        if(aux!=null){
            userRepository.save(user);
            user.setRole(aux);
        }

        String token = jwtUtils.generateToken(user); //gateway service
        return ResponseEntity.ok(token);
    }
}
