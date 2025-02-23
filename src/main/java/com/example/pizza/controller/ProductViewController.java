package com.example.pizza.controller;

import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import com.example.pizza.service.ProductService;
import com.example.pizza.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductViewController {
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductViewController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "add-product";
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("products", products);
            return "product-list";
        } catch (Exception e) {
            // Hata durumunda log
            e.printStackTrace();
            // Model'e hata mesajı ekleyebiliriz
            model.addAttribute("error", "Ürünler listelenirken bir hata oluştu");
            return "error"; // error.html template'i oluşturmanız gerekecek
        }
    }

    @PostMapping
    public String save(@RequestParam("image") MultipartFile file,
                       @RequestParam("name") String name,
                       @RequestParam("rating") double rating,
                       @RequestParam("stock") int stock,
                       @RequestParam("price") double price,
                       @RequestParam("categoryId") Long categoryId,
                       Model model) throws IOException {

        Category category = categoryService.findById(categoryId);
        Product product = new Product();
        product.setName(name);
        product.setRating(rating);
        product.setStock(stock);
        product.setPrice(price);
        product.setCategory(category);

        Product savedProduct = productService.save(product, file);

        model.addAttribute("imageURL", savedProduct.getImg());
        model.addAttribute("product", savedProduct);

        return "uploaded-product";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "edit-product";
    }
}