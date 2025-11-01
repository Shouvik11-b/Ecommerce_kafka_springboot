package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.repository.CartItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final CartItemRepository cartItemRepo;
    private final JdbcTemplate jdbcTemplate; // 👈 use direct SQL queries here

    public UserService(UserRepository userRepo,
                       CartItemRepository cartItemRepo,
                       JdbcTemplate jdbcTemplate) {
        this.userRepo = userRepo;
        this.cartItemRepo = cartItemRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Transactional
    public User addProductToCart(Long userId, Long productId, int quantity) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // ✅ Query product quantity directly from DB
        Integer availableQty = jdbcTemplate.queryForObject(
                "SELECT quantity FROM products WHERE id = ?",
                Integer.class,
                productId
        );

        if (availableQty == null) {
            throw new EntityNotFoundException("Product not found");
        }

        if (availableQty < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // ✅ Product is in stock — add to user's cart (no DB update to product)
        CartItem item = new CartItem();
        item.setUser(user);
        item.setQuantity(quantity);
        // Note: you can store productId separately if needed, or keep a minimal product reference object

        // Instead of product entity, we can just store a lightweight reference
        // (depends on your CartItem structure; if you remove @ManyToOne Product, add productId field)
        cartItemRepo.save(item);

        user.getCart().add(item);
        return userRepo.save(user);
    }

    public User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}

