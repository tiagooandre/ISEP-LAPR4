package eapli.base.productmanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Category;
import eapli.base.productmanagement.domain.Product;
import eapli.base.productmanagement.repositories.ProductRepository;
import eapli.framework.application.UseCaseController;

import java.util.List;

@UseCaseController
public class ListProductController {

    private final ListProductService listProd = new ListProductService();
    private final ProductRepository prod = PersistenceContext.repositories().products();
    private final CategoryDefineController defineCat = new CategoryDefineController();

    public Iterable<Product> allProducts() {
        return listProd.allProducts();
    }

    public List<Brand> productsByBrand() {
        return prod.findAllProductsByBrand();
    }

    public List<Product> prodByCat(Category category) {
        return prod.findByCategory(category);
    }


}
