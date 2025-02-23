package com.example.pizza.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;


@Entity
@Data
@Table(schema = "pizza",name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Kategori adı boş olamaz")
    @Size(max = 50, message = "Kategori adı en fazla 50 karakter olabilir")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Kategori resmi boş olamaz")
    @Column(name = "img")
    private String img;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products;
}
