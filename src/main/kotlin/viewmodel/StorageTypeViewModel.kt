package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.StorageType
import hu.otilsoft.catalog.repository.StorageTypeRepository
import java.lang.RuntimeException
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.GlobalCommand
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zul.Messagebox


class StorageTypeViewModel : EntityManagerViewModel() {
    private val repository: StorageTypeRepository by lazy { StorageTypeRepository(entityManager) }
    var storageTypes = repository.findAll().toMutableList()
    var newStorageType = StorageType()

    @GlobalCommand
    @NotifyChange("storageTypes")
    fun showStorageType() {
        storageTypes = repository.findAll().toMutableList()
    }

    @GlobalCommand
    @NotifyChange("storageTypes", "newStorageType")
    fun insertStorageType() {
        try {
            repository.save(newStorageType)
            storageTypes.add(newStorageType)
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
        } catch (ex: RuntimeException) {
            Messagebox.show(ex.message, "Warning", Messagebox.OK, Messagebox.EXCLAMATION)
        }
    }

    @GlobalCommand
    @NotifyChange("storageTypes")
    fun deleteStorageType(@BindingParam storageType: StorageType) {
        repository.delete(storageType)
        storageTypes.remove(storageType)
    }
}
