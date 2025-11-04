package com.example.ecommerce.payment.listner;

// src/main/java/com/example/payment/listener/OrderEventListener.java

import com.example.ecommerce.payment.model.OrderEvent;
import com.example.ecommerce.payment.model.PaymentResponseEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
public class OrderEventListener {

    private final KafkaTemplate<String, PaymentResponseEvent> kafkaTemplate;
    private final String paymentResponseTopic;

    public OrderEventListener(
            KafkaTemplate<String, PaymentResponseEvent> kafkaTemplate,
            @Value("${app.topic.payment-response-events}") String paymentResponseTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentResponseTopic = paymentResponseTopic;
    }

    @KafkaListener(topics = "${app.topic.order-events}", groupId = "${spring.kafka.consumer.group-id:payment-group}")
    public void onOrderEvent(OrderEvent event) {
        // simulate writing "successful" on console
        System.out.println("Payment successful for orderId: " + event.getOrderId()
                + ", userId: " + event.getUserId()
                + ", amount: " + event.getTotalAmount());

        if (event.getTotalAmount() > 1000) {
            PaymentResponseEvent response = new PaymentResponseEvent(
                    event.getOrderId(),
                    "HIGH_VALUE_ORDER"
            );

            kafkaTemplate.send(paymentResponseTopic, response);
            System.out.println("Sent high-value payment event for orderId: " + event.getOrderId());
        }
    }
}

