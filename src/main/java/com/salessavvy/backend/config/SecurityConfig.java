package com.salessavvy.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.salessavvy.backend.security.JwtAuthenticationFilter;

@Configuration
// This class contains configuration that Spring should load and use when starting the application.
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean // This tells spring to manage the object returned by this methodobject 
    // Spring Security needs a SecurityFilterChain object containing our security rules.
    // HttpSecurity is basically the object we use to configure those HTTP security rules.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
    .csrf(csrf -> csrf.disable())
    .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // The server doesn't remember the login using an HTTP session. The client presents its JWT on every request.
    )
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/roles").permitAll()
        .requestMatchers("/api/auth/register").permitAll()
        .requestMatchers("/api/auth/login").permitAll()
        .anyRequest().authenticated()   // Does the SecurityContext contain an authenticated Authentication?
    )
    .addFilterBefore(
        jwtAuthenticationFilter, // Run this filter before the bwloe default spring security login system and .anyRequest().authenticated() this say authenticated or not 
        UsernamePasswordAuthenticationFilter.class // Run 
    );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    // PasswordEncoder is the interface and BcryptPasswordEncoder is the implementaion of Bcrypt
    }

}