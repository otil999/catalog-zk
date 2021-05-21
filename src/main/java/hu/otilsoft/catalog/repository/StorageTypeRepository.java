package hu.otilsoft.catalog.repository;

import hu.otilsoft.catalog.model.StorageType;

import javax.persistence.EntityManager;
import java.util.List;

public class StorageTypeRepository extends AbstractRepository<StorageType> {
    public StorageTypeRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<StorageType> findAll() {
        return entityManager
                .createQuery("select st from storage_type st order by st.name", StorageType.class)
                .getResultList();
    }
}
