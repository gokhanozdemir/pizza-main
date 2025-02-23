package com.example.pizza.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dqjqkgpt3";
    private final String API_KEY = "526785153986271";
    private final String API_SECRET = "jJcztrT-qS5BpgQOVpdbMGDwXdY";

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", API_SECRET);

        Cloudinary cloudinary = new Cloudinary(config);

        // Cloudinary bağlantısını test etmek için ping metodu çağır
        try {
            ApiResponse apiResponse = cloudinary.api().createFolder("pizza", ObjectUtils.emptyMap());
            System.out.println("Cloudinary connection successful: " + apiResponse);
        } catch (Exception e) {
            System.err.println("Cloudinary connection failed: " + e.getMessage());
            throw new RuntimeException("Cloudinary configuration failed", e);
        }

        return cloudinary;
    }
}
