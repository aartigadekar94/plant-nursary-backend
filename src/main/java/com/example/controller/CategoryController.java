package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Categary;
import com.example.service.CategoryService;



@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public Categary createCategory(@RequestBody Categary category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/getAll")
    public List<Categary> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public Categary getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    
    @PutMapping("/{id}")
    public Categary updateCategory(@PathVariable Long id,
                                   @RequestBody Categary category) {
        return categoryService.updateCategory(id, category);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}

