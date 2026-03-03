package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.HeroSlider;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HeroSliderRepository extends JpaRepository<HeroSlider, String> {
    List<HeroSlider> findByActive(boolean active);
}

