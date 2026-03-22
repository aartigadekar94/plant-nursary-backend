package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.CartItem;
@EnableJpaRepositories
public interface CartItemDao extends JpaRepository<CartItem, Long>{
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);


}
