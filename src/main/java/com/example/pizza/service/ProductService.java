package com.example.pizza.service;

import com.example.pizza.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product findById(Long id);
    Product save(Product product, MultipartFile file) throws IOException;
    Product update(Long id, Product product, MultipartFile file) throws IOException;
    void delete(Product product);
}
