package com.salessavvy.backend.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.salessavvy.backend.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// A filter runs before your controller when an HTTP request comes into your application.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {  // run once for each request

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Spring can provide your CustomUserDetailsService because it is a bean

    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
           String authHeader = request.getHeader("Authorization"); // Authorization: Bearer eyJhbGciOi...
           if (authHeader == null || !authHeader.startsWith("Bearer ")) {
              filterChain.doFilter(request, response);  // Continue request normally
              return;
            }  
            String token = authHeader.substring(7); // Remove "Bearer " and keep only the JWT
            String extractedEmail = jwtService.extractEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(extractedEmail);
            System.out.println("EMAIL: " + userDetails.getUsername());
System.out.println("AUTHORITIES: " + userDetails.getAuthorities());

            UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication); // Now authenticated
        filterChain.doFilter(request, response);

    }
    
}

// Client
//   │
//   │ Authorization: Bearer JWT
//   ↓
// JwtAuthenticationFilter
//   │
//   ├── extract JWT
//   │
//   ↓
// JwtService
//   │
//   ├── verify signature
//   ├── verify expiration
//   └── extract sub
//   │
//   ↓
// alice@gmail.com
//   │
//   ↓
// UserDetailsService
//   │
//   ↓
// UserDetails
//   │
//   ↓
// Authentication
//   │
//   ↓
// SecurityContextHolder
//   │
//   ↓
// Spring Security:
// "This request is authenticated as Alice"
//   │
//   ↓
// Controller
