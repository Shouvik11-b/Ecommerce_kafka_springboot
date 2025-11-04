package com.example.ecommerce.order.service;

// src/main/java/com/example/order/service/OrderService.java

import com.example.ecommerce.order.entity.OrderEntity;
import com.example.ecommerce.order.model.OrderEvent;
import com.example.ecommerce.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private final String topic;
    private final OrderRepository orderRepository;

    public OrderService(KafkaTemplate<String, OrderEvent> kafkaTemplate,
                        @Value("${app.topic.order-events}") String topic,OrderRepository orderRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.orderRepository = orderRepository;
    }

    public OrderEvent createOrder(String userId, double totalAmount) {
//        String orderId = UUID.randomUUID().toString();

        OrderEntity orderEntity = new OrderEntity(userId, totalAmount);
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        OrderEvent event = new OrderEvent(savedOrder.getId().toString(), userId, totalAmount);
        // send event to kafka
        kafkaTemplate.send(topic, savedOrder.getId().toString(), event);
        return event;
    }
}
