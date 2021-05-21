package hu.otilsoft.catalog.repository;

import hu.otilsoft.catalog.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AbstractRepository<T extends AbstractEntity> {
    public static final Collator COLLATOR = Collator.getInstance(new Locale("hu"));
    private static final Logger log = Logger.getLogger(AbstractRepository.class.getName());

    static {
        COLLATOR.setStrength(Collator.PRIMARY);
    }

    final EntityManager entityManager;

    AbstractRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract List<T> findAll();

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            if (entity.getId() == 0) {
                entityManager.persist(entity);
            } else {
                entityManager.merge(entity);
            }
            transaction.commit();
        } catch (PersistenceException ex) {
            log.log(Level.WARNING, "Persistence exception occurred.", ex);
            transaction.rollback();
            if (ex.getCause().getCause().getMessage().contains("duplicate")) {
                throw new RuntimeException("Already exists");
            }
        }
        entityManager.refresh(entity);
    }

    public void delete(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.remove(entity);
            transaction.commit();
        } catch (PersistenceException ex) {
            log.log(Level.WARNING, "Persistence exception occurred.", ex);
            transaction.rollback();
        }
    }
}
