package com.salessavvy.backend.service;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.salessavvy.backend.dto.LoginResponse;
import com.salessavvy.backend.dto.UserResponse;
import com.salessavvy.backend.entity.Role;
import com.salessavvy.backend.entity.User;
import com.salessavvy.backend.repository.RoleRepository;
import com.salessavvy.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    // This is called dependency injection. 

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder =  passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
    }
    
    public void registerUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        // BCrypt is not encryption, It is hashing. You cannot do Bcrypt to Original
        // Use matches() to compare password during login
        // Bcrypt uses random salt and settings
        Role customerRole = roleRepository.findByName("CUSTOMER")
        .orElseThrow();
        user.setRole(customerRole);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Incorrect Password");
        }
        String token = jwtService.generateToken(user);
        LoginResponse loginResponse = new LoginResponse(user.getName(), user.getEmail(), user.getRole(), token);
        return loginResponse;
    }

    public UserResponse getCurrentUser(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return new UserResponse(
            user.getName(),
            user.getEmail(),
            user.getRole()
    );
}
}
