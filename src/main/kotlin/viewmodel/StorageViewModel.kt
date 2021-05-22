package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.Storage
import hu.otilsoft.catalog.repository.StorageRepository
import hu.otilsoft.catalog.repository.StorageTypeRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange


class StorageViewModel : EntityManagerViewModel() {
    private val storageRepository = StorageRepository(entityManager)
    private val storageTypeRepository = StorageTypeRepository(entityManager)
    val storageTypes = storageTypeRepository.findAll()
    var newStorage = Storage()
    var storages = storageRepository.findAll()

    init {
        newStorage.storageType = storageTypeRepository.findAll()[0]
    }

    @Command
    @NotifyChange("storages", "newStorage")
    fun insert() {
        storageRepository.save(newStorage)
        storages = storageRepository.findAll()
        createNewStorage()
    }

    @Command
    @NotifyChange("storages")
    fun delete(@BindingParam("storage") storage: Storage) {
        storageRepository.delete(storage)
        storages = storageRepository.findAll()
    }

    @Command
    @NotifyChange("storages")
    fun update(@BindingParam("storage") storage: Storage) {
        storageRepository.save(storage)
        storages = storageRepository.findAll()
    }

    private fun createNewStorage() {
        newStorage = Storage().apply { storageType = storageTypeRepository.findAll()[0] }
    }
}
