package com.salessavvy.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salessavvy.backend.entity.Category;
import com.salessavvy.backend.repository.CategoryRepository;

@Service
public class CategoryService {
        private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category does not exist"));
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Category retCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Now Category with this id exists."));
        retCategory.setDescription(category.getDescription());
        retCategory.setName(category.getName());
        return categoryRepository.save(retCategory);
    }

    public void deleteCategory(Long id) {
        if(categoryRepository.findById(id).isEmpty()) {
            throw new RuntimeException("No Category with this id exists.");
        }
        categoryRepository.deleteById(id);
    }
}
