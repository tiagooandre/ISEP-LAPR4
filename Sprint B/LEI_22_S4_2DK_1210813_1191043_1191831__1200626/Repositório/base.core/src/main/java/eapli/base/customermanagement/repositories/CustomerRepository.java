package eapli.base.customermanagement.repositories;

import eapli.base.customermanagement.domain.Customer;
import eapli.base.customermanagement.domain.VATCustomer;
import eapli.framework.domain.repositories.DomainRepository;
import eapli.framework.general.domain.model.Designation;

import java.util.concurrent.CompletionService;

public interface CustomerRepository extends DomainRepository<VATCustomer, Customer> {
    Iterable<Customer> findAllCustomers(Customer customer);
}
