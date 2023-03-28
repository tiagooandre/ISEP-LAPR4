package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.framework.visitor.Visitor;

public class WarehousePrinter implements Visitor<Warehouse> {

    @Override
    public void visit(Warehouse visit) {
        System.out.printf("ID: %d | Name: %s | Length: %.1f | Width: %.1f | Square: %.1f | Unit: %s\n",
                visit.getId(),
                visit.getName(),
                visit.getLength(),
                visit.getWidth(),
                visit.getSquare(),
                visit.getUnit());
    }
}