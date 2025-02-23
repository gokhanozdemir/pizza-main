package com.example.pizza.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(schema = "pizza",name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Ürün adı boş olamaz")
    @Size(max = 100, message = "Ürün adı en fazla 100 karakter olabilir")
    @Column(name = "name", nullable = false)
    private String name;

    @Min(value = 0, message = "Puan 0'dan küçük olamaz")
    @Max(value = 5, message = "Puan 5'ten büyük olamaz")
    @Column(name = "rating")
    private double rating;

    @Min(value = 0, message = "Stok miktarı negatif olamaz")
    @Column(name = "stock")
    private int stock;

    @Positive(message = "Fiyat sıfırdan büyük olmalıdır")
    @Column(name = "price")
    private double price;

    @NotBlank(message = "Ürün resmi boş olamaz")
    @Column(name = "img")
    private String img;

    @ManyToOne(optional = false) // Ürün mutlaka bir kategoriye sahip olmalı
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Kategori boş olamaz")
    @JsonBackReference
    private Category category;
}
