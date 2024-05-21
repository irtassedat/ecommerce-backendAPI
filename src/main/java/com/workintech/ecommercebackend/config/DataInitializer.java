package com.workintech.ecommercebackend.config;

import com.workintech.ecommercebackend.entity.Role;
import com.workintech.ecommercebackend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner init(RoleRepository roleRepository) {
        return args -> {
            // Veritabanında ROLE_USER varsa kaydetmeyi atlar, yoksa kaydeder
            if (!roleRepository.existsByName("ROLE_USER")) {
                Role userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            // Veritabanında ROLE_ADMIN varsa kaydetmeyi atlar, yoksa kaydeder
            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                Role adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }
        };
    }
}
