package com.example.ecommerce.order.service;

// src/main/java/com/example/order/service/OrderService.java

import com.example.ecommerce.order.model.OrderEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final String topic;

    public OrderService(KafkaTemplate<String, OrderEvent> kafkaTemplate,
                        @Value("${app.topic.name}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public OrderEvent createOrder(String userId, double totalAmount) {
        String orderId = UUID.randomUUID().toString();
        OrderEvent event = new OrderEvent(orderId, userId, totalAmount);
        // send event to kafka
        kafkaTemplate.send(topic, orderId, event);
        return event;
    }
}
