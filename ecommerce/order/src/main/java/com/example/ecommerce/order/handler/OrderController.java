// src/main/java/com/example/order/controller/OrderController.java
package com.example.ecommerce.order.handler;

import com.example.ecommerce.order.model.OrderEvent;
import com.example.ecommerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    // Accepts JSON payload: { "userId": "u1", "totalAmount": 123.45 }
    @PostMapping
    public ResponseEntity<OrderEvent> createOrder(@RequestBody Map<String, Object> payload) {
        String userId = (String) payload.get("userId");
        Number amt = (Number) payload.get("totalAmount");
        if (userId == null || amt == null) {
            return ResponseEntity.badRequest().build();
        }
        double totalAmount = amt.doubleValue();
        OrderEvent created = orderService.createOrder(userId, totalAmount);
        return ResponseEntity.created(URI.create("/orders/" + created.getOrderId())).body(created);
    }
}
