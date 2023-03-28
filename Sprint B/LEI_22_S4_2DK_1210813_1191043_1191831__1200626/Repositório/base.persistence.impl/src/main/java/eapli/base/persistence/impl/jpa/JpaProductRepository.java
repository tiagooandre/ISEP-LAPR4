package eapli.base.persistence.impl.jpa;

import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Category;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.io.util.Console;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JpaProductRepository extends BaseJpaRepositoryBase<Product, Long, Long> implements ProductRepository {

    JpaProductRepository() {
        super("id");
    }

    @Override
    public List<Brand> findAllProductsByBrand() {

        Brand input = Brand.valueOf(Console.readLine("Insert Brand: "));

        TypedQuery<Brand> query = entityManager().createQuery("SELECT p FROM Product p WHERE p.brand = :input", Brand.class);
        query.setParameter("input", input);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return null;
    }

    @Override
    public List<Category> findAllCategories() {
        return null;
    }

    @Override
    public Optional<Product> ofIdentity(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteOfIdentity(Long entityId) {
    }
}
