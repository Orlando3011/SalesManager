package pl.psk.salesManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.psk.salesManager.model.Owner;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    List<Owner> findAll();
}
