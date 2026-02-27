package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.GalleryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryImageRepository extends JpaRepository<GalleryImage, String> {
}
