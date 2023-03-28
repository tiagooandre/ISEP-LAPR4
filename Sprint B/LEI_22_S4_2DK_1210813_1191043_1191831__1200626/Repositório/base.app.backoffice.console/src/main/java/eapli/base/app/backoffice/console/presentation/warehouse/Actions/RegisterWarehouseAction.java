package eapli.base.app.backoffice.console.presentation.warehouse.Actions;

import eapli.base.app.backoffice.console.presentation.warehouse.RegisterWarehouseUI;
import eapli.framework.actions.Action;

public class RegisterWarehouseAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterWarehouseUI().show();
    }
}