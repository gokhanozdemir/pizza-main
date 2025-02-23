package com.example.pizza.controller;

import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import com.example.pizza.service.ProductService;
import com.example.pizza.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductRestController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    public Product save(@RequestParam("image") MultipartFile file,
                        @RequestParam("name") String name,
                        @RequestParam("rating") double rating,
                        @RequestParam("stock") int stock,
                        @RequestParam("price") double price,
                        @RequestParam("categoryId") Long categoryId) throws IOException {

        Category category = categoryService.findById(categoryId);
        Product product = new Product();
        product.setName(name);
        product.setRating(rating);
        product.setStock(stock);
        product.setPrice(price);
        product.setCategory(category);

        return productService.save(product, file);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id,
                          @RequestParam(value = "image", required = false) MultipartFile file,
                          @RequestParam("name") String name,
                          @RequestParam("rating") double rating,
                          @RequestParam("stock") int stock,
                          @RequestParam("price") double price,
                          @RequestParam("categoryId") Long categoryId) throws IOException {

        Product existingProduct = productService.findById(id);
        Category category = categoryService.findById(categoryId);

        existingProduct.setName(name);
        existingProduct.setRating(rating);
        existingProduct.setStock(stock);
        existingProduct.setPrice(price);
        existingProduct.setCategory(category);

        return productService.update(id, existingProduct, file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws IOException {
        Product product = productService.findById(id);
        productService.delete(product);
    }
}
