package com.example.ecommerce.order.service;

import com.example.ecommerce.order.model.OrderEvent;
import com.example.ecommerce.order.model.PaymentResponseEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class PaymentResponseListner {
    @KafkaListener(topics = "${app.topic.payment-response-events}", groupId = "${spring.kafka.consumer.group-id:payment-group}")
    public void onPaymentResponseEvent(PaymentResponseEvent event){
        System.out.println("Payment successful for orderId: " + event.getStatus()
                + ", userId: " + event.getOrderId()
                );
    }
}
