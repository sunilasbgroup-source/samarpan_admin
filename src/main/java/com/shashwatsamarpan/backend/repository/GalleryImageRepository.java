package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, String> {
    List<GalleryImage> findByCategory(String category);
}
