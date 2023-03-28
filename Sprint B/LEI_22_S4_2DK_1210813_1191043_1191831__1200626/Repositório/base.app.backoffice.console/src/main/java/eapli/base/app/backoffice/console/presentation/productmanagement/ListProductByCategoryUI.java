/**package eapli.base.app.backoffice.console.presentation.productmanagement;

import eapli.base.productmanagement.application.CategoryDefineController;
import eapli.base.productmanagement.domain.Category;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

import java.util.List;

public class ListProductByCategoryUI extends AbstractUI {
    @Override
    protected boolean doShow() {
        private static final CategoryDefineController prodList = new CategoryDefineController();

        List<Category> cat = prodList.findAllCategories();
        prodList.printCategoryList(cat);
        String name = Console.readLine("Category: ");
        prodList.printCategoryList(name);

        return false;
    }

    @Override
    public String headline() {
        return "Catalog Filter >\n";
    }
}
*/