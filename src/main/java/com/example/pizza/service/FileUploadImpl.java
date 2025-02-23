package com.example.pizza.service;

import com.cloudinary.Cloudinary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileUploadImpl implements FileUpload {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Map<String, String> options = new HashMap<>();
        options.put("folder", "pizza"); // Cloudinary'de "pizza" klasörüne kaydet
        options.put("public_id", UUID.randomUUID().toString()); // Benzersiz dosya adı

        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), options)
                .get("url")
                .toString();
    }

    @Override
    public String deleteFile(String imageURL) throws IOException {
        try {
            // Cloudinary URL'den public_id'yi çıkaralım
            String publicId = extractPublicId(imageURL);

            Map<String, String> options = new HashMap<>();
            options.put("invalidate", "true"); // Önbellekten temizlemek için

            cloudinary.uploader().destroy(publicId, options);

            return "File successfully deleted from Cloudinary: " + publicId;
        } catch (Exception e) {
            throw new IOException("Error while deleting file from Cloudinary: " + e.getMessage());
        }
    }

    private String extractPublicId(String imageURL) {
        // Cloudinary URL genellikle şu formatta olur: https://res.cloudinary.com/{cloud_name}/image/upload/v1234567890/pizza/filename.jpg
        // Buradan public_id'yi çıkartmalıyız: "pizza/filename"
        int lastSlashIndex = imageURL.lastIndexOf("/");
        int lastDotIndex = imageURL.lastIndexOf(".");

        if (lastSlashIndex == -1 || lastDotIndex == -1 || lastDotIndex <= lastSlashIndex) {
            throw new IllegalArgumentException("Invalid Cloudinary URL: " + imageURL);
        }

        return "pizza/" + imageURL.substring(lastSlashIndex + 1, lastDotIndex);
    }
}
