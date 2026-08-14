package com.salessavvy.backend.dto;

import com.salessavvy.backend.entity.Role;

public class UserResponse {

    private String name;
    private String email;
    private Role role;

    public UserResponse() {
    }

    public UserResponse(String name, String email, Role role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email)  {
        this.email = email;
    }

    public Role getRole() {
    return role;
}

public void setRole(Role role) {
    this.role = role;
}
}
