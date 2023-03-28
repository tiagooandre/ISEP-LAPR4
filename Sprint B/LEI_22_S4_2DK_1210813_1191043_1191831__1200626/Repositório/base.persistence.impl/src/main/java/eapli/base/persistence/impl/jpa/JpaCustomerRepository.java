package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.domain.VATCustomer;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

import javax.persistence.TypedQuery;
import java.util.Optional;

public class JpaCustomerRepository extends JpaAutoTxRepository<Customer, VATCustomer, VATCustomer> implements CustomerRepository {
    public JpaCustomerRepository(TransactionalContext autoTx) {
        super(autoTx, "VAT");
    }

    public JpaCustomerRepository(String PUName) {
        super(PUName, Application.settings().getExtendedPersistenceProperties(), "VAT");
    }

    @Override
    public Iterable<Customer> findAllCustomers(Customer customer) {
        return null;
    }
}
