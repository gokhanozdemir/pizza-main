package com.example.pizza.service;

import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category findById(Long id);
    Category save(Category category, MultipartFile file) throws IOException;
    Category update(Long id, Category category, MultipartFile file) throws IOException;
    void delete(Category category);
    List<Product> getAllProductsByCategory(Long categoryId);
}