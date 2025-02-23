package com.example.pizza.service;

import com.example.pizza.entity.Product;
import com.example.pizza.exceptions.ApiException;
import com.example.pizza.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final FileUpload fileUpload;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, FileUpload fileUpload) {
        this.productRepository = productRepository;
        this.fileUpload = fileUpload;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiException("Product not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Product save(Product product, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imageUrl = fileUpload.uploadFile(file);
            product.setImg(imageUrl);
        }
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product product, MultipartFile file) throws IOException {
        Product existingProduct = findById(id);

        if (file != null && !file.isEmpty()) {
            try {
                fileUpload.deleteFile(existingProduct.getImg());
            } catch (IOException e) {
                throw new ApiException("Error deleting old image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            String newImageUrl = fileUpload.uploadFile(file);
            existingProduct.setImg(newImageUrl);
        }

        existingProduct.setName(product.getName());
        existingProduct.setRating(product.getRating());
        existingProduct.setStock(product.getStock());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());

        return productRepository.save(existingProduct);
    }

    @Override
    public void delete(Product product) {
        try {
            fileUpload.deleteFile(product.getImg());
        } catch (IOException e) {
            throw new ApiException("Error deleting product image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        productRepository.delete(product);
    }
}
