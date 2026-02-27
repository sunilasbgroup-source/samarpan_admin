package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.Brochure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrochureRepository extends JpaRepository<Brochure, String> {
}
