package hu.otilsoft.catalog.repository;

import javax.persistence.EntityManager;
import java.util.List;

import hu.otilsoft.catalog.model.Media;

public class MediaRepository extends AbstractRepository<Media> {
    public MediaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Media> findAll() {
        return entityManager
                .createQuery("select m from Media m order by m.name", Media.class)
                .getResultList();
    }
}
