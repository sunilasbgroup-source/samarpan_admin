package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.AdmissionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdmissionFormRepository extends JpaRepository<AdmissionForm, String> {
    List<AdmissionForm> findByActive(boolean active);
}
