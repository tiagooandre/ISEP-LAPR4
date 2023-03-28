package eapli.base.warehousemanagement.application;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.warehousemanagement.domain.AGVDock;
import eapli.base.warehousemanagement.repositories.AGVDocksRepository;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;

public class ListAGVDockController {
    private final AuthorizationService auth = AuthzRegistry.authorizationService();
    private final AGVDocksRepository repo = PersistenceContext.repositories().agvdocks();

    public Iterable<AGVDock> allAGVDocks() {
        auth.ensureAuthenticatedUserHasAnyOf(BaseRoles.POWER_USER, BaseRoles.MENU_MANAGER, BaseRoles.SALES_CLERK);

        return repo.findAll();
    }
}