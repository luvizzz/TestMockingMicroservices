package app.resource;

import app.domain.Lease;
import app.domain.LeaseId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseRepository extends CrudRepository<Lease, LeaseId> {
    List<Lease> findAll();

    List<Lease> findAllByCustomer(String customer);
}
