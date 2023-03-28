package eapli.base.persistence.impl.inmemory;

import eapli.base.warehousemanagement.domain.AGVDock;
import eapli.base.warehousemanagement.repositories.AGVDocksRepository;
import eapli.framework.infrastructure.repositories.impl.inmemory.InMemoryDomainRepository;

public class InMemoryAGVDockRepository extends InMemoryDomainRepository<AGVDock, String> implements AGVDocksRepository {

    static {
        InMemoryInitializer.init();
    }
}