package pl.psk.salesManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.psk.salesManager.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
