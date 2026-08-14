package com.salessavvy.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;

    @ManyToOne
    @JoinColumn(name= "role_id")
    private Role role;

    public User() {
        // Create no-argument constructor so when JPA/hibernate  can instantiate this entity when reading a row from MYSQL
    }

    public User(String userName, String email, String password, Role role) {
      this.userName = userName;
      this.email = email;
      this.password = password;
      this.role  = role;
    }

    public String getPassword() {
    return password;
    }

   public void setPassword(String password) {
    this.password = password;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
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
