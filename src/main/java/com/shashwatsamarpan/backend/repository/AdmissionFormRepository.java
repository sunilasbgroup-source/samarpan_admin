package com.shashwatsamarpan.backend.repository;

import com.shashwatsamarpan.backend.model.AdmissionForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdmissionFormRepository extends JpaRepository<AdmissionForm, String> {
}
