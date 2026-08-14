package com.salessavvy.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.salessavvy.backend.entity.Role;
import com.salessavvy.backend.repository.RoleRepository;

@Configuration
public class DataInitializer {

    // CommandLineRunner = code that Spring runs once when the application starts
    @Bean
    CommandLineRunner initializeRoles(RoleRepository roleRepository) {

        return args -> {

            // Check if ADMIN already exists.
            // If it doesn't exist, save it to the database.
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(new Role("ADMIN"));
            }

            // Check if CUSTOMER already exists.
            // If it doesn't exist, save it to the database.
            if (roleRepository.findByName("CUSTOMER").isEmpty()) {
                roleRepository.save(new Role("CUSTOMER"));
            }
        };
    }
}