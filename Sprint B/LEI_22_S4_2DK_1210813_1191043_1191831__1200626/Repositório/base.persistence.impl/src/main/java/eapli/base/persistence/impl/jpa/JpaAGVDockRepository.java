package eapli.base.persistence.impl.jpa;

import eapli.base.warehousemanagement.domain.AGVDock;
import eapli.base.warehousemanagement.repositories.AGVDocksRepository;

public class JpaAGVDockRepository extends BaseJpaRepositoryBase<AGVDock, String, String> implements AGVDocksRepository {

    JpaAGVDockRepository() {
        super("id");
    }
}