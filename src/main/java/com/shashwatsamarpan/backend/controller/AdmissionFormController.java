package com.shashwatsamarpan.backend.controller;

import com.shashwatsamarpan.backend.model.AdmissionForm;
import com.shashwatsamarpan.backend.repository.AdmissionFormRepository;
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
public class AdmissionFormController {

    @Autowired
    private AdmissionFormRepository repository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/public/admission-forms")
    public List<AdmissionForm> getActiveForms() {
        return repository.findByActive(true);
    }

    @GetMapping("/admission-forms")
    @PreAuthorize("hasRole('ADMIN')")
    public List<AdmissionForm> getAllForms() {
        return repository.findAll();
    }

    @PostMapping("/admission-forms")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> uploadForm(@RequestParam("file") MultipartFile file,
                                       @RequestParam("title") String title) throws IOException {
        Map uploadResult = cloudinaryService.upload(file);
        String fileUrl = (String) uploadResult.get("url");

        AdmissionForm form = new AdmissionForm();
        form.setTitle(title);
        form.setFileUrl(fileUrl);
        form.setActive(true);

        return ResponseEntity.ok(repository.save(form));
    }

    @DeleteMapping("/admission-forms/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteForm(@PathVariable String id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admission-forms/{id}/toggle")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleForm(@PathVariable String id) {
        return repository.findById(id).map(form -> {
            form.setActive(!form.isActive());
            return ResponseEntity.ok(repository.save(form));
        }).orElse(ResponseEntity.notFound().build());
    }
}
