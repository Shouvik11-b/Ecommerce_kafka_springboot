package com.example.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Instead of referencing Product entity, store only productId
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Min(1)
    private int quantity;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
