package eapli.base.warehousemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.domain.Warehouse;
import eapli.base.warehousemanagement.repositories.WarehouseRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListWarehouseService {
    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final WarehouseRepository dishRepository = PersistenceContext.repositories().warehouses();

    public Iterable<Warehouse> allWarehouses() {
        authz.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MENU_MANAGER);

        return dishRepository.findAll();
    }
}