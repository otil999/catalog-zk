package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.StorageType
import hu.otilsoft.catalog.repository.StorageTypeRepository
import java.lang.RuntimeException
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zul.Messagebox


class StorageTypeViewModel : EntityManagerViewModel() {
    private val repository = StorageTypeRepository(entityManager)
    var storageTypes = repository.findAll()
    var newStorageType = StorageType()

    @Command
    @NotifyChange("storageTypes")
    fun refresh() {
        storageTypes = repository.findAll()
    }

    @Command
    @NotifyChange("storageTypes", "newStorageType")
    fun insert() {
        try {
            repository.save(newStorageType)
            storageTypes = repository.findAll()
            newStorageType = StorageType()
        } catch (ex: RuntimeException) {
            Messagebox.show(ex.message, "Warning", Messagebox.OK, Messagebox.EXCLAMATION)
        }
    }

    @Command
    @NotifyChange("storageTypes")
    fun update(@BindingParam storageType: StorageType) {
        try {
            repository.save(storageType)
            storageTypes = repository.findAll()
        } catch (ex: RuntimeException) {
            Messagebox.show(ex.message, "Warning", Messagebox.OK, Messagebox.EXCLAMATION)
        }
    }

    @Command
    @NotifyChange("storageTypes")
    fun delete(@BindingParam storageType: StorageType) {
        repository.delete(storageType)
        storageTypes = repository.findAll()
    }
}
