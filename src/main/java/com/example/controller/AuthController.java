package com.example.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.dto.LoginRequestDTO;
import com.example.dto.SignUpRequestDTO;
import com.example.dto.CustomerResponseDTO;
import com.example.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService userService;

    public AuthController(AuthService customerService) {
        this.userService = customerService;
    }

    // =========================
    // SIGNUP
    // =========================
    @PostMapping("/signup")
    public ResponseEntity<CustomerResponseDTO> register(
          @Valid @RequestBody SignUpRequestDTO dto) {

        return ResponseEntity.ok(userService.register(dto));
    }

    // =========================
    // LOGIN
    // =========================
//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @RequestBody LoginRequestDTO dto) {
//
//        return ResponseEntity.ok(userService.login(dto));
//    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = authentication.getName();

        String role = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        Map<String, String> response = new HashMap<>();
        response.put("email", email);
        response.put("role", role);

        return ResponseEntity.ok(response);
    }
//  
}