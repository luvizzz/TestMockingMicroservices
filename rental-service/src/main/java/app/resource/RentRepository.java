package app.resource;

import app.domain.Rent;
import app.domain.RentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<Rent, RentId> {
    List<Rent> findAll();

    List<Rent> findAllByCustomer(String customer);
}
