package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerResponseDTO;

import com.example.dto.UserResponseDTO;
import com.example.entity.Customer;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // ✅ Get logged-in customer profile
    @GetMapping("/user/me")
    public ResponseEntity<UserResponseDTO> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.ok(
                customerService.getCurrentuser(userDetails.getUsername())
        );
    }
    @GetMapping("/admin/all")
    public ResponseEntity<List<CustomerResponseDTO>> getAllCust() {

        return ResponseEntity.ok(
                customerService.getAllCustomer());
    }


    // ✅ Update logged-in customer profile
    @PutMapping("/user/update")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @RequestBody Customer updateDTO,
            Authentication auth) {
    	String email = auth.getName();

         return ResponseEntity.ok(
                customerService.updateCustomer(email, updateDTO)
        );
    }

    // ✅ Delete own account
    @DeleteMapping("/user/delete")
    public ResponseEntity<String> deleteProfile(
            String email) {

        

        customerService.deleteCustomer(email);

        return ResponseEntity.ok("Account deleted successfully");
    }
}
