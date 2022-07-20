package hu.otilsoft.catalog

import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener


const val SESSION_ATTRIBUTE_NAME = "entity-manager"


class CatalogSessionListener : HttpSessionListener {
    override fun sessionCreated(se: HttpSessionEvent) {
        val session = se.session
        if (session.getAttribute(SESSION_ATTRIBUTE_NAME) == null) {
            val entityManagerFactory = Persistence.createEntityManagerFactory("catalog")
            val entityManager = entityManagerFactory.createEntityManager()
            session.setAttribute(SESSION_ATTRIBUTE_NAME, entityManager)
            session.servletContext.log("Entity manager is initialized.")
        }
    }

    override fun sessionDestroyed(se: HttpSessionEvent) {
        val session = se.session
        val entityManager = session.getAttribute(SESSION_ATTRIBUTE_NAME) as EntityManager
        entityManager.close()
        session.servletContext.log("Entity manager is closed.")
    }
}
