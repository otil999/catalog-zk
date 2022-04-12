package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.repository.SeriesRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.GlobalCommand
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zk.ui.event.InputEvent


class SeriesViewModel : EntityManagerViewModel() {
    private val repository: SeriesRepository by lazy { SeriesRepository(entityManager) }
    var series = mutableListOf<Series>()
    var newSeries = Series()
    var searchText = ""

    @GlobalCommand
    @NotifyChange("series")
    fun showSeries() {
        series = repository.findAll().toMutableList()
    }

    @Command
    @NotifyChange("series", "newSeries")
    fun insert() {
        repository.save(newSeries)
        series.add(newSeries)
        newSeries = Series()
    }

    @Command
    @NotifyChange("series")
    fun update(@BindingParam item: Series) {
        repository.save(item)
    }

    @Command
    @NotifyChange("series")
    fun delete(@BindingParam item: Series) {
        repository.delete(item)
        series.remove(item)
    }

    @Command
    @NotifyChange("series", "searchText")
    fun search(@BindingParam event: InputEvent) {
        searchText = event.value
        series = repository.findByName(searchText).toMutableList()
    }
}
