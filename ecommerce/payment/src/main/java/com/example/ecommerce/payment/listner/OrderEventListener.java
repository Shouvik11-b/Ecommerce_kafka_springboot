package com.example.ecommerce.payment.listner;

// src/main/java/com/example/payment/listener/OrderEventListener.java

import com.example.ecommerce.payment.model.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    @KafkaListener(topics = "${app.topic.name}", groupId = "${spring.kafka.consumer.group-id:payment-group}")
    public void onOrderEvent(OrderEvent event) {
        // simulate writing "successful" on console
        System.out.println("Payment successful for orderId: " + event.getOrderId()
                + ", userId: " + event.getUserId()
                + ", amount: " + event.getTotalAmount());
    }
}

