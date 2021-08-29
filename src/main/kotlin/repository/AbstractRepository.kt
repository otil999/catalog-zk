package hu.otilsoft.catalog.repository

import hu.otilsoft.catalog.AbstractEntity
import org.zkoss.zk.ui.Sessions
import java.text.Collator
import java.time.LocalDateTime
import java.util.Locale
import java.util.logging.Level
import java.util.logging.Logger
import javax.persistence.EntityManager
import javax.persistence.PersistenceException
import javax.persistence.RollbackException
import javax.servlet.ServletContext
import javax.servlet.http.HttpSession

abstract class AbstractRepository<T : AbstractEntity>(val entityManager: EntityManager) {
    private val context: ServletContext = (Sessions.getCurrent().nativeSession as HttpSession).servletContext
    val collator: Collator = Collator.getInstance(Locale("hu")).apply { strength = Collator.PRIMARY }

    abstract fun findAll(): List<T>

    fun save(entity: T) {
        val transaction = entityManager.transaction.apply { begin() }
        try {
            if (entity.id == 0L) {
                entityManager.persist(entity)
            } else {
                entityManager.merge(entity)
                entity.updated = LocalDateTime.now()
            }
            transaction.commit()
        } catch (ex: PersistenceException) {
            transaction.rollback()
            context.log("Persistence exception occurred.", ex)
            if (ex.cause?.cause?.message?.contains("duplicate") == true) {
                throw RuntimeException("Already exists")
            }
        }
    }

    fun delete(entity: T) {
        val transaction = entityManager.transaction.apply { begin() }
        try {
            entityManager.remove(if (entityManager.contains(entity)) entity else entityManager.merge(entity))
            transaction.commit()
        } catch (ex: PersistenceException) {
            transaction.rollback()
            context.log("Persistence exception occurred.", ex)
        }
    }
}
