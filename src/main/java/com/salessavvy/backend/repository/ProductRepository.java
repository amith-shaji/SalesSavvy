package com.salessavvy.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salessavvy.backend.entity.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findByName(String name);
    // List<Product> findAllByCategory(Category category); or 
    List<Product> findAllByCategoryCategoryId(Long categoryId);

    List<Product> findAllByNameContaining(String name);

    List<Product> findAllByNameContainingAndCategoryCategoryId(String name, Long categoryId);
}
