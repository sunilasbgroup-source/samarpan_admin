package com.shashwatsamarpan.backend.config;

import com.shashwatsamarpan.backend.model.Admin;
import com.shashwatsamarpan.backend.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.findByEmail("jainakshay1237@gmail.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("jainakshay1237@gmail.com");
            admin.setPassword(passwordEncoder.encode("asbGroup@123"));
            admin.setRoles(new HashSet<>(Collections.singletonList("ROLE_ADMIN")));
            adminRepository.save(admin);
            System.out.println("Admin user seeded successfully.");
        }
    }
}
