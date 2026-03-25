package com.example.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderRequestDTO;
import com.example.dto.OrderResponseDTO;
import com.example.entity.Order;
import com.example.service.OrderSevice;

@RestController
@RequestMapping("/orders")

public class OrderController {

    private final OrderSevice orderService;

    public OrderController(OrderSevice orderService) {
        this.orderService = orderService;
    }

    // ✅ Place Order
    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(Authentication authentication,@RequestBody OrderRequestDTO address) {

        String email = authentication.getName();

        orderService.placeOrder(email,address);

        return ResponseEntity.ok("Order placed successfully");
    }

    // ✅ Get My Orders
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(orderService.getMyOrders(email));
    }
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders()
    {
    	 return ResponseEntity.ok(orderService.getAllOrders());
    	
    }
    
    
    @PutMapping("/admin/orders/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam String status){

      

    	return ResponseEntity.ok(orderService.updateDelivaryStatus(id,status));
    }
    
}