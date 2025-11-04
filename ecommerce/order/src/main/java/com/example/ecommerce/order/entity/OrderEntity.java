package com.example.ecommerce.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userId;

    @NotNull(message = "Total amount cannot be null")
    @Min(value = 1, message = "Total amount must be at least 1")
    Double totalAmount;

    public OrderEntity() {}

    public OrderEntity(String userId, Double totalAmount) {
        this.userId = userId;
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}
