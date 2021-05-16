package app.resource;

import app.domain.Lease;
import app.domain.LeaseId;
import app.domain.Payment;
import app.domain.PaymentId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, PaymentId> {
    List<Payment> findAll();

    List<Payment> findAllByCustomerAndLeaseId(String customer, Long lease);
}
