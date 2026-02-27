package com.shashwatsamarpan.backend.controller;

import com.shashwatsamarpan.backend.model.Brochure;
import com.shashwatsamarpan.backend.repository.BrochureRepository;
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
public class BrochureController {

    @Autowired
    private BrochureRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/public/brochures")
    public List<Brochure> getBrochures() {
        return repository.findAll();
    }

    @PostMapping("/brochures")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadBrochure(@RequestParam("file") MultipartFile file,
                                          @RequestParam("title") String title) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String fileUrl = (String) uploadResult.get("url");

        Brochure brochure = new Brochure();
        brochure.setTitle(title);
        brochure.setFileUrl(fileUrl);

        return ResponseEntity.ok(repository.save(brochure));
    }

    @DeleteMapping("/brochures/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBrochure(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
