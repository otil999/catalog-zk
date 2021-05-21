package hu.otilsoft.catalog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class CatalogSessionListener implements HttpSessionListener {
    public static final String SESSION_ATTRIBUTE_NAME = "entity-manager";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        if (session.getAttribute(SESSION_ATTRIBUTE_NAME) == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("catalog");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            session.setAttribute(SESSION_ATTRIBUTE_NAME, entityManager);
            session.getServletContext().log("Entity manager is initialized.");
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        EntityManager entityManager = (EntityManager) session.getAttribute(SESSION_ATTRIBUTE_NAME);
        if (entityManager != null) {
            entityManager.close();
            session.getServletContext().log("Entity manager is closed.");
        }
    }
}
