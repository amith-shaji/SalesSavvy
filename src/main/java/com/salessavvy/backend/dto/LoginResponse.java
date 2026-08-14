package com.salessavvy.backend.dto;

import com.salessavvy.backend.entity.Role;

public class LoginResponse {
    private String name;
    private String email;
    private Role role;
    private String token;

    public LoginResponse() {

    }

    public LoginResponse(String name, String email, Role role,  String token) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public String getEmail() {
       return email;
    }

    public String getName() {
       return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
