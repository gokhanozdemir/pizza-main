package com.example.pizza.controller;

import com.example.pizza.entity.Category;
import com.example.pizza.entity.Product;
import com.example.pizza.service.CategoryService;
import com.example.pizza.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            List<Product> products = productService.getAllProducts();

            model.addAttribute("totalCategories", categories.size());
            model.addAttribute("totalProducts", products.size());
            model.addAttribute("totalStock", products.stream().mapToInt(Product::getStock).sum());

            return "/admin/dashboard";
        } catch (Exception e) {
            model.addAttribute("error", "Dashboard yüklenirken bir hata oluştu");
            return "/error";
        }
    }

    @GetMapping("/products")
    public String products(Model model) {
        try {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/admin/products";
        } catch (Exception e) {
            model.addAttribute("error", "Ürünler listelenirken bir hata oluştu");
            return "/error";
        }
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        try {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/admin/categories";
        } catch (Exception e) {
            model.addAttribute("error", "Kategoriler listelenirken bir hata oluştu");
            return "/error";
        }
    }
}