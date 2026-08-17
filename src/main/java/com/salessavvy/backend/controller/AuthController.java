package com.salessavvy.backend.controller;

import com.salessavvy.backend.repository.UserRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salessavvy.backend.dto.LoginRequest;
import com.salessavvy.backend.dto.LoginResponse;
import com.salessavvy.backend.dto.UserResponse;
import com.salessavvy.backend.entity.User;
import com.salessavvy.backend.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

@PostMapping("/register")
public String register(@RequestBody User user) {
    userService.registerUser(user);
    return "User registered successfully";
}

@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest loginRequest)
{
    return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
}

@GetMapping("/me")
public UserResponse me() {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    return userService.getCurrentUser(email);
}


// We can give authroization to methods instaed of url in SecurityConfig. This is called method level authorization.
// Before executing this method, check whether the authenticated user has ROLE_ADMIN
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/test")
public String adminTest() {
    return "You are an admin!";
}


// If an Exeption happens in this controller, run this
@ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex) {
        return ex.getMessage();
    }

    
}
