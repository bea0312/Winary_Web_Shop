package hr.winary.webshop.jwpwinary.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Purchase")
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "wine_id")
    private Wine wine;

    private LocalDateTime purchaseTime;
    private int quantity;
    private double totalPrice;
    @Getter
    @Setter
    private String paymentMethod;

}
