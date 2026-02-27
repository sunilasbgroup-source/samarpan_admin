package com.shashwatsamarpan.backend.controller;

import com.shashwatsamarpan.backend.model.HeroSlider;
import com.shashwatsamarpan.backend.repository.HeroSliderRepository;
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
public class HeroSliderController {

    @Autowired
    private HeroSliderRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/public/hero-sliders")
    public List<HeroSlider> getActiveSliders() {
        return repository.findByActive(true);
    }

    @GetMapping("/hero-sliders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<HeroSlider> getAllSliders() {
        return repository.findAll();
    }

    @PostMapping("/hero-sliders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSlider(@RequestParam("file") MultipartFile file,
                                         @RequestParam("title") String title,
                                         @RequestParam("subtitle") String subtitle) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String imageUrl = (String) uploadResult.get("url");

        HeroSlider slider = new HeroSlider();
        slider.setTitle(title);
        slider.setSubtitle(subtitle);
        slider.setImageUrl(imageUrl);
        slider.setActive(true);

        return ResponseEntity.ok(repository.save(slider));
    }

    @DeleteMapping("/hero-sliders/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSlider(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hero-sliders/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleSlider(@PathVariable String id) {
        return repository.findById(id).map(slider -> {
            slider.setActive(!slider.isActive());
            return ResponseEntity.ok(repository.save(slider));
        }).orElse(ResponseEntity.notFound().build());
    }
}
