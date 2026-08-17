package com.salessavvy.backend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salessavvy.backend.dto.ProductRequest;
import com.salessavvy.backend.entity.Category;
import com.salessavvy.backend.entity.Product;
import com.salessavvy.backend.exception.ProductNotFoundException;
import com.salessavvy.backend.repository.CategoryRepository;
import com.salessavvy.backend.repository.ProductRepository;

@Service
public class ProductService {
        private final ProductRepository productRepository;
        private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product findProductByName(String name) {
        return productRepository.findByName(name).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category does not exist"));
        Product product = new Product(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice(), productRequest.getStockQuantity(), productRequest.getImageUrl(), category);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product retProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No Product with this id exists."));
        retProduct.setDescription(productRequest.getDescription());
        retProduct.setName(productRequest.getName());
        retProduct.setImageUrl(productRequest.getImageUrl());
        retProduct.setPrice(productRequest.getPrice());
        retProduct.setStockQuantity(productRequest.getStockQuantity());
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new RuntimeException("Category does not exist"));
        retProduct.setCategory(category);
        return productRepository.save(retProduct);
    }

    public void deleteProduct(Long id) {
        if(productRepository.findById(id).isEmpty()) {
            throw new ProductNotFoundException("No Product with this id exists.");
        }
        productRepository.deleteById(id);
    }

    public List<Product> findproductsByCategory(Long id) {
        return productRepository.findAllByCategoryCategoryId(id);
    }

    public List<Product> searchProductsByName(String name) {
    return productRepository.findAllByNameContaining(name);
   }

    public List<Product> searchProductsByNameAndCategory(String name, Long id) {
    return productRepository.findAllByNameContainingAndCategoryCategoryId(name, id);
   }
}
