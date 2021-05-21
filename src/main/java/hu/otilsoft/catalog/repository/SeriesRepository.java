package hu.otilsoft.catalog.repository;

import javax.persistence.EntityManager;
import java.util.List;

import hu.otilsoft.catalog.model.Series;

public class SeriesRepository extends AbstractRepository<Series> {
    public SeriesRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Series> findAll() {
        return entityManager.createQuery("select s from Series s order by s.name", Series.class).getResultList();
    }
}
