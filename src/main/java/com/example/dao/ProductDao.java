package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.Product;
@EnableJpaRepositories
public interface ProductDao extends JpaRepository<Product, Long> {
	
	List<Product> findByCategoryId(Long categaryId);
	List<Product> findByNameContainingIgnoreCase(String name);

}
