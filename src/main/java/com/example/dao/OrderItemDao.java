package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.OrderItem;

@EnableJpaRepositories
public interface OrderItemDao extends JpaRepository<OrderItem, Long>{

}
