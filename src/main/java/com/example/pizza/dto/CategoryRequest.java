package com.example.pizza.dto;

import jakarta.validation.constraints.*;

public record CategoryRequest(
        String name,
        String img
) {}
