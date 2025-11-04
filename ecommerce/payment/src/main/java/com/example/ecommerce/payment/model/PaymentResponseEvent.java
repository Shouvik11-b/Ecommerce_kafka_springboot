package com.example.ecommerce.payment.model;


import java.io.Serializable;
import java.util.Objects;

public class PaymentResponseEvent implements Serializable {
    private String orderId;
    private String status;


    public PaymentResponseEvent() {}

    public PaymentResponseEvent(String orderId, String status) {
        this.orderId = orderId;
        this.status = status;

    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentResponseEvent that = (PaymentResponseEvent) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status);
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId='" + orderId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}

