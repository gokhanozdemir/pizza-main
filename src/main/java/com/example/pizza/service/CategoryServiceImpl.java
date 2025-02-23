package com.example.pizza.service;

import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import com.example.pizza.exceptions.ApiException;
import com.example.pizza.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final FileUpload fileUpload;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUpload fileUpload) {
        this.categoryRepository = categoryRepository;
        this.fileUpload = fileUpload;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApiException("Category not found with id: " + id, HttpStatus.NOT_FOUND));
    }



    @Override
    public Category save(Category category, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileUpload.uploadFile(file);
            category.setImg(imageUrl);
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, Category category, MultipartFile file) throws IOException {
        Category existingCategory = findById(id);

        if (file != null && !file.isEmpty()) {
            try {
                fileUpload.deleteFile(existingCategory.getImg());
            } catch (IOException e) {
                throw new ApiException("Error deleting old image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String newImageUrl = fileUpload.uploadFile(file);
            existingCategory.setImg(newImageUrl);
        }

        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Category category) {
        try {
            fileUpload.deleteFile(category.getImg());
        } catch (IOException e) {
            throw new ApiException("Error deleting category image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        categoryRepository.delete(category);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long categoryId) {
        Category category = findById(categoryId);
        return category.getProducts();
    }
}