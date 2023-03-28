package eapli.base.productmanagement.repositories;

import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Category;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.domain.repositories.DomainRepository;

import java.util.List;

public interface ProductRepository extends DomainRepository<Long, Product> {

    List<Brand> findAllProductsByBrand();

    List<Product> findByCategory(Category category);

    List<Category> findAllCategories();

}
