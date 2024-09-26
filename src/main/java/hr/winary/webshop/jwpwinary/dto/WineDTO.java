package hr.winary.webshop.jwpwinary.dto;

import hr.winary.webshop.jwpwinary.model.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class WineDTO {
    private Long id;
    private String name;
    private Integer categoryId;
    private double price;
    private double liters;
    private String description;
    private String yearOfHarvest;
    private String imageName;
}
