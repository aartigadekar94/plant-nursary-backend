package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.Customer;
import com.example.entity.User;
@EnableJpaRepositories
public interface CustomerDao extends JpaRepository<Customer, Long>{
	
	Optional<Customer> findByUser(User user);

	Optional<Customer> findByUserEmail(String email);

}
