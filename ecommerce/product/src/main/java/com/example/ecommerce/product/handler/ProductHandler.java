package com.example.ecommerce.product.handler;

import com.example.ecommerce.product.entity.Product;
import com.example.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductHandler {


    private final ProductService service;


    public ProductHandler(ProductService service) {
        this.service = service;
    }


    // 1) Add product
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product saved = service.addProduct(product);
        return ResponseEntity.created(URI.create("/products/" + saved.getId())).body(saved);
    }


    // 2) Get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }


    // 3) Search products by name
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }
}