package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.User;

@EnableJpaRepositories
public interface UserDao extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

}
