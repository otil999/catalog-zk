package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.model.StorageType
import hu.otilsoft.catalog.repository.StorageRepository
import hu.otilsoft.catalog.repository.StorageTypeRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.GlobalCommand
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zul.Messagebox
import java.lang.RuntimeException


class StorageViewModel : EntityManagerViewModel() {
    private val storageRepository = StorageRepository(entityManager)
    private val storageTypeRepository by lazy { StorageTypeRepository(entityManager) }
    var storageTypes = storageTypeRepository.findAll()
    var newStorage = createNewStorage()
    var storages = storageRepository.findAll().toMutableList()

    @Command
    @NotifyChange("storages", "newStorage")
    fun insert() {
        storageRepository.save(newStorage)
        with(storages) {
            add(newStorage)
            sortBy { it.name }
        }
        newStorage = createNewStorage()
    }

    @Command
    @NotifyChange("storages")
    fun delete(@BindingParam("storage") storage: Storage) {
        storageRepository.delete(storage)
        storages.remove(storage)
    }

    @Command
    @NotifyChange("storages")
    fun update(@BindingParam("storage") storage: Storage) {
        storageRepository.save(storage)
        storages.sortBy { it.name }
    }

    private fun createNewStorage(): Storage = Storage().apply { storageType = storageTypes.first() }

    @GlobalCommand
    @NotifyChange("storageTypes")
    fun insertStorageType() {
        storageTypes = storageTypeRepository.findAll()
    }

    @GlobalCommand
    @NotifyChange("storageTypes")
    fun deleteStorageType(@BindingParam storageType: StorageType) {
        storageTypes = storageTypeRepository.findAll()
    }
}
