package hr.winary.webshop.jwpwinary.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CompletedOrder {
    private String status;
    private String token;
    private Integer totalPrice;

    public CompletedOrder(String status, String token, Integer totalPrice) {
        this.status = status;
        this.token = token;
        this.totalPrice = totalPrice;
    }

    public CompletedOrder(String status) {
        this.status = status;
    }

}

