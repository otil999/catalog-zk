package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.CatalogSessionListener;
import hu.otilsoft.catalog.DoubleConverter;
import hu.otilsoft.catalog.LocalDateTimeConverter;
import org.zkoss.zk.ui.Sessions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public abstract class EntityManagerViewModel {
    private static final Logger logger = Logger.getLogger(EntityManagerViewModel.class.getName());
    final EntityManager entityManager;
    final LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
    final DoubleConverter doubleConverter = new DoubleConverter();

    public EntityManagerViewModel() {
        HttpSession httpSession = (HttpSession) Sessions.getCurrent().getNativeSession();
        logger.info("Session:" + httpSession);
        entityManager = (EntityManager) httpSession.getAttribute(CatalogSessionListener.SESSION_ATTRIBUTE_NAME);
        logger.info("Entity manager:" + entityManager);
    }

    public LocalDateTimeConverter getLocalDateTimeConverter() {
        return localDateTimeConverter;
    }

    public DoubleConverter getDoubleConverter() {
        return doubleConverter;
    }
}
