package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.CartDao;
import com.example.dao.CartItemDao;
import com.example.dao.CustomerDao;
import com.example.dto.CartRequestDTO;
import com.example.dto.CartResponseDTO;
import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.entity.Customer;
import com.example.service.CartService;

@RestController
@RequestMapping("/cart")

public class CartController {
	@Autowired
	private CartService service;
	
	@Autowired
	private CustomerDao custRepo;
	
	
	 // ✅ Add To Cart
	@PostMapping("/add")
	public ResponseEntity<String> addToCart(
	        @RequestBody CartRequestDTO request,
	        Authentication authentication) {

	    String email = authentication.getName();

	    Customer customer = custRepo.findByUserEmail(email)
	            .orElseThrow(() -> new RuntimeException("Customer not found"));

	    service.addToCart(
	            customer.getId(),
	            request.getProductId(),
	            request.getQuantity()
	    );

	    return ResponseEntity.ok("Product added to cart");
	}

//	
//	@GetMapping("/my-cart")
//	public ResponseEntity<CartResponseDTO> viewCart(Authentication authentication) {
//
//	    String email = authentication.getName();
//
//	    return ResponseEntity.ok(service.getCartByEmail(email));
//	}
	
	@GetMapping("/my-cart")
	public ResponseEntity<CartResponseDTO> getCart(Authentication auth) {
		CartResponseDTO cart = service.getCartByEmail(auth.getName());

	    return ResponseEntity.ok()
	            .cacheControl(CacheControl.noStore())
	            .body(cart);
	}
	
	@DeleteMapping("/item/{cartItemId}")
	public ResponseEntity<String> deleteCartItem(
	        @PathVariable Long cartItemId,
	        Authentication authentication) {

	    service.deleteCartItem(authentication.getName(), cartItemId);

	    return ResponseEntity.ok("Item deleted");
	}

}
