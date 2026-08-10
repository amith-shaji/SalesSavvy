package com.salessavvy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salessavvy.backend.entity.Role;
import com.salessavvy.backend.repository.RoleRepository;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
