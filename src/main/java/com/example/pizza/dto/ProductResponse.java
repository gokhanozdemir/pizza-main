package com.example.pizza.dto;

public record ProductResponse(
        Long id,
        String name,
        double rating,
        int stock,
        double price,
        String img,
        Long categoryId
) {}
