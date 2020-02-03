package pl.psk.salesManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.psk.salesManager.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
