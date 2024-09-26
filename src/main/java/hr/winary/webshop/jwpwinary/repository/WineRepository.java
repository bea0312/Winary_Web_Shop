package hr.winary.webshop.jwpwinary.repository;

import hr.winary.webshop.jwpwinary.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WineRepository extends JpaRepository<Wine, Long> {
    List<Wine> findAllByCategoryId(Integer id);
}
