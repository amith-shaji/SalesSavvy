package com.salessavvy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salessavvy.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
