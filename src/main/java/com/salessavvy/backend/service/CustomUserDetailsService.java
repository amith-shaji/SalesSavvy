package com.salessavvy.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salessavvy.backend.entity.User;
import com.salessavvy.backend.repository.UserRepository;

// Spring Security doesn't simply work with a raw JWT string.
// It needs an Authentication object inside its SecurityContext
// UserDetails is basically the information Spring Security uses to construct that Authentication
// We're taking the one credential the client sent—the JWT—and turning it into something Spring Security understands.
// So it can answer questions like is it authenticated request
@Service
public class CustomUserDetailsService implements UserDetailsService{ // UserDetailsService is a Spring Security interface
    // Given a username, find that user's security information.

    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    // UserDetails is  Spring Security's representation of a user for authentication/authorization.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
        .withUsername(user.getEmail())
        .password(user.getPassword())
        .roles(user.getRole().getName())
        .build();
    }
    
}
