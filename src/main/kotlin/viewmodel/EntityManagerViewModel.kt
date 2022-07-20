package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.DoubleConverter
import hu.otilsoft.catalog.LocalDateTimeConverter
import hu.otilsoft.catalog.SESSION_ATTRIBUTE_NAME
import org.zkoss.zk.ui.Sessions
import javax.persistence.EntityManager
import javax.servlet.http.HttpSession


abstract class EntityManagerViewModel {
    private val httpSession = Sessions.getCurrent().nativeSession as HttpSession
    val entityManager = httpSession.getAttribute(SESSION_ATTRIBUTE_NAME) as EntityManager
    val localDateTimeConverter = LocalDateTimeConverter()
    val doubleConverter = DoubleConverter()


    fun log(message: String, throwable: Throwable? = null) {
        httpSession.servletContext.log(message, throwable)
    }
}
