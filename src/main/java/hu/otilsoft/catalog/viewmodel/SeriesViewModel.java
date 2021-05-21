package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.model.Series;
import hu.otilsoft.catalog.model.StorageType;
import hu.otilsoft.catalog.repository.SeriesRepository;
import hu.otilsoft.catalog.repository.StorageTypeRepository;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.List;

public class SeriesViewModel extends EntityManagerViewModel {
    private final SeriesRepository repository;
    private Series newSeries;

    public SeriesViewModel() {
        repository = new SeriesRepository(entityManager);
        newSeries = new Series();
    }

    @Command @NotifyChange("series")
    public void insert() {
        repository.save(newSeries);
        newSeries = new Series();
    }

    @Command @NotifyChange("series")
    public void update(@BindingParam Series series) {
        repository.save(series);
    }

    @Command @NotifyChange("series")
    public void delete(@BindingParam Series series) {
        repository.delete(series);
    }

    public List<Series> getSeries() {
        return repository.findAll();
    }

    public Series getNewSeries() {
        return newSeries;
    }
}
