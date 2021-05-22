package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.Series
import hu.otilsoft.catalog.repository.SeriesRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange


class SeriesViewModel : EntityManagerViewModel() {
    private val repository = SeriesRepository(entityManager)
    var series = repository.findAll()
    var newSeries = Series()

    @Command
    @NotifyChange("series")
    fun insert() {
        repository.save(newSeries)
        series = repository.findAll()
        newSeries = Series()
    }

    @Command
    @NotifyChange("series")
    fun update(@BindingParam series: Series) {
        repository.save(series)
        this.series = repository.findAll()
    }

    @Command
    @NotifyChange("series")
    fun delete(@BindingParam series: Series) {
        repository.delete(series)
        this.series = repository.findAll()
    }
}
