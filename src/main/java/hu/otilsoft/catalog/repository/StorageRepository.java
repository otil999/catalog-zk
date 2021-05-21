package hu.otilsoft.catalog.repository;

import hu.otilsoft.catalog.model.Storage;

import javax.persistence.EntityManager;
import java.util.List;

public class StorageRepository extends AbstractRepository<Storage> {
    public StorageRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Storage> findAll() {
        return entityManager.createQuery("select s from Storage s order by s.name", Storage.class).getResultList();
    }
}
