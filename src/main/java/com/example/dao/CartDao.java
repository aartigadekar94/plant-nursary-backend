package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.Cart;
@EnableJpaRepositories
public interface CartDao extends JpaRepository<Cart, Long> {
	 Optional<Cart> findByCustomerId(Long customerId);
	 Optional<Cart> findByCustomerUserEmail(String email);
	

}
