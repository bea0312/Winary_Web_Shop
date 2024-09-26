package hr.winary.webshop.jwpwinary.repository;

import hr.winary.webshop.jwpwinary.model.Purchase;
import hr.winary.webshop.jwpwinary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUser(User user);
}