package hr.winary.webshop.jwpwinary.repository;

import hr.winary.webshop.jwpwinary.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
