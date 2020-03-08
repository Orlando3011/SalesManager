package pl.psk.salesManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.psk.salesManager.model.SoldProduct;

@Repository
public interface SoldProductRepository extends JpaRepository<SoldProduct, Integer> {
}
