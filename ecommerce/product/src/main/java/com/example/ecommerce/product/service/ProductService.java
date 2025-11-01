package com.example.ecommerce.product.service;

import com.example.ecommerce.product.entity.Product;
import com.example.ecommerce.product.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ProductService {


    private final ProductRepository repository;


    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    @CacheEvict(value = "products", allEntries = true)
    public Product addProduct(Product product) {
        return repository.save(product);
    }


    @Cacheable(value = "products", key = "'all'")
    public List<Product> getAllProducts() {
// heavy DB call cached in Redis
        return repository.findAll();
    }


    @Cacheable(value = "products", key = "'search:' + #name")
    public List<Product> searchByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}