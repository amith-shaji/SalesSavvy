package com.salessavvy.backend.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salessavvy.backend.dto.ProductRequest;
import com.salessavvy.backend.entity.Product;
import com.salessavvy.backend.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product createProduct(@RequestBody ProductRequest productRequest) {
       return productService.createProduct(productRequest);
    }

    @GetMapping("/{name}")
    public Product getProductDetails(@PathVariable String name) {
        return productService.findProductByName(name);
    }

    @GetMapping("category/{id}")
    public List<Product> getProductsByCategory(@PathVariable Long id) {
        return productService.findproductsByCategory(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
        @RequestParam String name,
        @RequestParam(required = false) Long categoryId) {

    if (categoryId != null) {
        return productService.searchProductsByNameAndCategory(name, categoryId);
    }

    return productService.searchProductsByName(name);
   }

    @GetMapping
    public Page<Product> getAllProductDetails(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Product updateProductDetails(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
         return productService.updateProduct(id, productRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProductDetails(@PathVariable Long id) {
         productService.deleteProduct(id);
    }
    

    
}
