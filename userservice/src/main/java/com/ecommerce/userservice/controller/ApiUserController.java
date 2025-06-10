package com.ecommerce.userservice.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.entity.UserDTO;
import com.ecommerce.userservice.entity.EmailChangeRequest;
import com.ecommerce.userservice.entity.PasswordResetRequest;


@RestController
@RequestMapping("/user")
public class ApiUserController {
    private final UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;  
    @Autowired
    JwtUtil jwtUtils;

    public ApiUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/info/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        
        User user=userRepository.findByUsername(username);
        UserDTO res=null;
        if(user!=null){
            res=new UserDTO(user.getUser_id(), user.getUsername(), user.getPassword(), user.getRole().getRole(), user.getLastPasswordReset(), user.getEmail(), user.getOldEmail());
        }

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @PostMapping("/resetpwd/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username, @RequestBody PasswordResetRequest req, HttpServletRequest request) {

        if (!req.new_password.equals(req.confirm_password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords don't match");
        }

        if (req.new_password.length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password too short");
        }

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, req.password)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is incorrect");
        }

        User user=userRepository.findByUsername(username);
        user.setPassword(encoder.encode(req.new_password));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Password was updated");
        
    }
        
    @PostMapping("/changemail/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username, @RequestBody EmailChangeRequest req, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, req.password)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password is incorrect");
        }

        User user=userRepository.findByUsername(username);
        user.setOldEmail(user.getEmail());
        user.setEmail(req.email);
        user.setLastPasswordReset(); //Used to check when last change was made
        userRepository.save(user); 
        
        String token = jwtUtils.generateToken(user); 
        return ResponseEntity.ok(token);
    }


   
}
    