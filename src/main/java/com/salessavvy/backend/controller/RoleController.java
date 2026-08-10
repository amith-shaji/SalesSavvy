package com.salessavvy.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salessavvy.backend.entity.Role;
import com.salessavvy.backend.service.RoleService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/roles")
public class RoleController {
     private final RoleService roleService;

     public RoleController(RoleService roleService) {
        this.roleService = roleService;
     }

     @GetMapping
     public List<Role> getRoles() {
         return roleService.getAllRoles();
     }
     

    
}
