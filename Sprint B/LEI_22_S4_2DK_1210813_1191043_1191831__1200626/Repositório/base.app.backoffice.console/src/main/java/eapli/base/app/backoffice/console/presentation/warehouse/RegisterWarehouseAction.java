package eapli.base.app.backoffice.console.presentation.warehouse;

import eapli.framework.actions.Action;

public class RegisterWarehouseAction implements Action {

    @Override
    public boolean execute() {
        return new RegisterWarehouseUI().show();
    }
}