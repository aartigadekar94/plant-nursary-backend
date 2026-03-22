package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.entity.Categary;

@EnableJpaRepositories
public interface CategaryDao extends JpaRepository<Categary, Long> {

}
