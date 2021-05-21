package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.model.Storage;
import hu.otilsoft.catalog.model.StorageType;
import hu.otilsoft.catalog.repository.StorageRepository;
import hu.otilsoft.catalog.repository.StorageTypeRepository;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.List;

public class StorageViewModel extends EntityManagerViewModel {
    private final StorageRepository storageRepository;
    private final StorageTypeRepository storageTypeRepository;
    private Storage newStorage;

    public StorageViewModel() {
        storageRepository = new StorageRepository(entityManager);
        storageTypeRepository = new StorageTypeRepository(entityManager);
        createNewStorage();
    }

    public List<Storage> getStorages() {
        return storageRepository.findAll();
    }

    public List<StorageType> getStorageTypes() {
        return storageTypeRepository.findAll();
    }

    @Command @NotifyChange({"storages", "newStorage"})
    public void insert() {
        storageRepository.save(newStorage);
        createNewStorage();
    }

    @Command @NotifyChange("storages")
    public void delete(@BindingParam("storage") Storage storage) {
        System.out.println("XXXX:"+storage);
        storageRepository.delete(storage);
    }

    @Command @NotifyChange("storages")
    public void save(@BindingParam("storage") Storage storage) {
        System.out.println("XXXX2:"+storage);
        storageRepository.save(storage);
    }

    public Storage getNewStorage() {
        return newStorage;
    }

    private void createNewStorage() {
        newStorage = new Storage();
        newStorage.setStorageType(getStorageTypes().get(0));
    }
}
