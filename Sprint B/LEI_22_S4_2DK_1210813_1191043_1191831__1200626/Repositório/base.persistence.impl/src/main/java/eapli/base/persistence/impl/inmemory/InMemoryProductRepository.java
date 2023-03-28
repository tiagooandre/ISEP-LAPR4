
package eapli.base.persistence.impl.inmemory;

import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Category;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.general.domain.model.Designation;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository extends InMemoryDomainRepository<Product, Long> implements ProductRepository {

    static {
        InMemoryInitializer.init();
    }

    @Override
    public Optional<Product> ofIdentity(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(Long entityId) {

    }

    @Override
    public List<Brand> findAllProductsByBrand() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return null;
    }

    @Override
    public List<Category> findAllCategories() {
        return null;
    }
}
