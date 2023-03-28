package eapli.base.warehousemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.repositories.WarehouseRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class RegisterWarehouseController {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final WarehouseRepository dishRepository = PersistenceContext.repositories().warehouses();

    public Warehouse registerWarehouse(final String name, final float length, final float width, final float square, final String unit) {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MENU_MANAGER, BaseRoles.SALES_CLERK);

        return dishRepository.save(new Warehouse(name, length, width, square, unit));
    }
}