package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.model.Media;
import hu.otilsoft.catalog.model.StorageType;
import hu.otilsoft.catalog.repository.MediaRepository;
import hu.otilsoft.catalog.repository.StorageTypeRepository;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.List;

public class StorageTypeViewModel extends EntityManagerViewModel {
    private final StorageTypeRepository repository;
    private StorageType newStorageType;

    public StorageTypeViewModel() {
        repository = new StorageTypeRepository(entityManager);
        newStorageType = new StorageType();
    }

    @Command @NotifyChange("storageTypes")
    public void insert() {
        repository.save(newStorageType);
        newStorageType = new StorageType();
    }

    @Command @NotifyChange("storageTypes")
    public void update(@BindingParam StorageType storageType) {
        repository.save(storageType);
    }

    @Command @NotifyChange("storageTypes")
    public void delete(@BindingParam StorageType storageType) {
        repository.delete(storageType);
    }

    public List<StorageType> getStorageTypes() {
        return repository.findAll();
    }

    public StorageType getNewStorageType() {
        return newStorageType;
    }
}
