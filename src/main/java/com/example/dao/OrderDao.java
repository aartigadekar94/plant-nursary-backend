package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.Order;
@EnableJpaRepositories
public interface OrderDao extends JpaRepository<Order, Long> {
	 List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);
}
