package hr.winary.webshop.jwpwinary.model;

import lombok.Data;

@Data
public class PaymentOrder {
    private String status;
    private String orderId;
    private String redirectUrl;

    public PaymentOrder(String status) {
        this.status = status;
    }

    public PaymentOrder(String status, String orderId, String redirectUrl) {
        this.status = status;
        this.orderId = orderId;
        this.redirectUrl = redirectUrl;
    }

    // Getters and Setters
}

