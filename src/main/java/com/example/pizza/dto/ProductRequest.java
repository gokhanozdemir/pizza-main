package com.example.pizza.dto;

import jakarta.validation.constraints.*;

public record ProductRequest(
        String name,
        double rating,
        int stock,
        double price,
        String img,
        Long categoryId
) {}
