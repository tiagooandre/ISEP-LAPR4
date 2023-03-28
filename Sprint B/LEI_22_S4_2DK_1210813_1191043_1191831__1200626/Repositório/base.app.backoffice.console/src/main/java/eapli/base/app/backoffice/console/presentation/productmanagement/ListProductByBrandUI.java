package eapli.base.app.backoffice.console.presentation.productmanagement;

import eapli.base.productmanagement.application.ListProductController;
import eapli.base.productmanagement.domain.Brand;
import eapli.base.productmanagement.domain.Product;
import eapli.framework.presentation.console.AbstractListUI;
import eapli.framework.visitor.Visitor;

public class ListProductByBrandUI extends AbstractListUI<Brand> {

    private final ListProductController theController = new ListProductController();
    private final BrandPrinter printer = new BrandPrinter();

    @Override
    protected Iterable<Brand> elements() {
        return this.theController.productsByBrand();
    }

    @Override
    protected Visitor<Brand> elementPrinter() {
        return printer;
    }

    @Override
    protected String elementName() {
        return "Brand";
    }

    @Override
    protected String listHeader() {
        return "Brands";
    }

    @Override
    protected String emptyMessage() {
        return "No data\n";
    }

    @Override
    public String headline() {
        return "List Brands";
    }


}