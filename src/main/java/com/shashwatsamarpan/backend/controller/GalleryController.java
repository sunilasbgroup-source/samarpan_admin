package com.shashwatsamarpan.backend.controller;

import com.shashwatsamarpan.backend.model.GalleryImage;
import com.shashwatsamarpan.backend.repository.GalleryImageRepository;
import com.shashwatsamarpan.backend.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class GalleryController {

    @Autowired
    private GalleryImageRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/public/gallery")
    public List<GalleryImage> getGallery(@RequestParam(required = false) String category) {
        if (category != null) {
            return repository.findByCategory(category);
        }
        return repository.findAll();
    }

    @PostMapping("/gallery")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file,
                                     @RequestParam("title") String title,
                                     @RequestParam("category") String category) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");

        GalleryImage image = new GalleryImage();
        image.setTitle(title);
        image.setCategory(category);
        image.setImageUrl(imageUrl);

        return ResponseEntity.ok(repository.save(image));
    }

    @DeleteMapping("/gallery/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
