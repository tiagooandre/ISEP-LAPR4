package eapli.base.persistence.impl.inmemory;

import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.domain.VATCustomer;
import eapli.base.customermanagement.repositories.CustomerRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryCustomerRepository extends InMemoryDomainRepository<Customer, VATCustomer> implements CustomerRepository{
    static {
        InMemoryInitializer.init();
    }

    @Override
    public Iterable<Customer> findAllCustomers(Customer customer) {
        return null;
    }

}
