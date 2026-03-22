package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.CategaryDao;
import com.example.entity.Categary;



@Service
public class CategoryService {
   
    private final CategaryDao categoryRepository;

    public CategoryService(CategaryDao categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Categary createCategory(Categary category) {
        return categoryRepository.save(category);
    }

    public List<Categary> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Categary getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
    
    public Categary updateCategory(Long id, Categary updatedCategory) {
        Categary category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(updatedCategory.getName());
        category.setDiscription(updatedCategory.getDiscription());

        return categoryRepository.save(category);
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

