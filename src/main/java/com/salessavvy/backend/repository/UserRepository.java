package com.salessavvy.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salessavvy.backend.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
}
