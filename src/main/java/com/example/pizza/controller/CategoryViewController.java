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
@RequestMapping("/category")
public class CategoryViewController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public CategoryViewController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "add-category";
    }

    @GetMapping("/list")
    public String listCategories(Model model) {
        try {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "category-list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Kategoriler listelenirken bir hata oluştu");
            return "error";
        }
    }

    @PostMapping
    public String save(@RequestParam("image") MultipartFile file,
                       @RequestParam("name") String name,
                       Model model) throws IOException {
        Category category = new Category();
        category.setName(name);

        Category savedCategory = categoryService.save(category, file);

        model.addAttribute("imageURL", savedCategory.getImg());
        model.addAttribute("category", savedCategory);

        return "uploaded-category";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        List<Product> products = categoryService.getAllProductsByCategory(id);

        model.addAttribute("category", category);
        model.addAttribute("products", products);

        return "edit-category";
    }
}