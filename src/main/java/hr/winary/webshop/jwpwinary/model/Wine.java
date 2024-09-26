package hr.winary.webshop.jwpwinary.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private double price;
    private double liters;
    private String description;
    private String yearOfHarvest;
    private String imageName;
}
