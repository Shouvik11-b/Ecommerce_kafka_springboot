package com.example.ecommerce.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    private String name;


    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType type;


    @Min(0)
    private double price;


    @Min(0)
    private int quantity;


    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public ProductType getType() { return type; }
    public void setType(ProductType type) { this.type = type; }


    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }


    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
