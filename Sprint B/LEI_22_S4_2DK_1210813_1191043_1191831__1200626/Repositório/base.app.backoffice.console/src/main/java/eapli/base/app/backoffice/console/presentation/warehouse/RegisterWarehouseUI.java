package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.base.warehousemanagement.application.RegisterWarehouseController;
import eapli.framework.io.util.Console;
import eapli.framework.presentation.console.AbstractUI;

public class RegisterWarehouseUI extends AbstractUI {
    private final RegisterWarehouseController theController = new RegisterWarehouseController();

    @Override
    protected boolean doShow() {
        final String name = Console.readLine("Name: ");
        final float length = (float) Console.readDouble("Length: ");
        final float width = (float) Console.readDouble("Width: ");
        final float square = (float) Console.readDouble("Square: ");
        final String unit = Console.readLine("Unit: ");

        try {
            this.theController.registerWarehouse(name, length, width, square, unit);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public String headline() {
        return "Register Warehouse";
    }
}