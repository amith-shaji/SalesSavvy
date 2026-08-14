package com.salessavvy.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salessavvy.backend.entity.Role;
import com.salessavvy.backend.service.RoleService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/roles")
public class RoleController {
     private final RoleService roleService;

     public RoleController(RoleService roleService) {
        this.roleService = roleService;
     }
     
     // This method may never be reached because spring security asks  authentication and authorization before controller
     // So you have to configure Spring security first

     @GetMapping
     public List<Role> getRoles() {
         return roleService.getAllRoles();
     }
     

    
}
