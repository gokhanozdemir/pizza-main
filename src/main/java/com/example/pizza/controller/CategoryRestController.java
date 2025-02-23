package com.example.pizza.controller;

import com.example.pizza.dto.CategoryResponse;
import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import com.example.pizza.service.CategoryService;
import com.example.pizza.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
public class CategoryRestController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryRestController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.getAllCategories();
    }
    @GetMapping("/simple")
    public List<CategoryResponse> getAllCategoriesWithoutProducts() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(category -> new CategoryResponse(
                        category.getId(),
                        category.getName(),
                        category.getImg()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/{id}/products")
    public List<Product> getCategoryProducts(@PathVariable Long id) {
        return categoryService.getAllProductsByCategory(id);
    }

    @PostMapping
    public Category save(@RequestParam("image") MultipartFile file,
                         @RequestParam("name") String name) throws IOException {
        Category category = new Category();
        category.setName(name);
        return categoryService.save(category, file);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id,
                           @RequestParam(value = "image", required = false) MultipartFile file,
                           @RequestParam("name") String name) throws IOException {
        Category category = new Category();
        category.setName(name);
        return categoryService.update(id, category, file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        categoryService.delete(category);
    }
}