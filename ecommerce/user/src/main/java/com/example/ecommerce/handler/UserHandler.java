package com.example.ecommerce.handler;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saved = userService.createUser(user);
        return ResponseEntity.created(URI.create("/users/" + saved.getId())).body(saved);
    }

    @PostMapping("/{userId}/cart")
    public ResponseEntity<User> addProductToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        User updated = userService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }
}
