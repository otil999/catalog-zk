package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Movie
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.repository.*
import org.zkoss.bind.annotation.*
import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Messagebox
import java.time.LocalDateTime


class MovieEditViewModel : EntityManagerViewModel() {
    private val movieRepository: MovieRepository by lazy { MovieRepository(entityManager) }
    private val seriesRepository: SeriesRepository by lazy { SeriesRepository(entityManager) }
    private val storageRepository: StorageRepository by lazy { StorageRepository(entityManager) }

    var editingMovie = Movie()
    var series = mutableListOf<Series>()
    var storages = mutableListOf<Storage>()

    private var affectedSeries: Series? = null
    private var oldCounter: Int? = null

    @GlobalCommand
    @NotifyChange("editingMovie", "series", "storages")
    fun editMovie(@BindingParam("item") movie: Movie?) {
        storages = storageRepository.findAll().toMutableList()
        series = seriesRepository.findAll().toMutableList().apply { add(0, Series()) }
        editingMovie = movie ?: Movie()
        affectedSeries = editingMovie.series
        oldCounter = movie?.counter
    }

    @Command
    fun save() {
        if (editingMovie.series?.name?.isBlank() == true) {
            editingMovie.series = null
        }
        if (oldCounter != editingMovie.counter) {
            editingMovie.lastViewed = LocalDateTime.now()
        }
        try {
            movieRepository.save(editingMovie, affectedSeries)
            Messagebox.show("Movie is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        } catch (ex: Exception) {
            Messagebox.show("Error when saving movie.", "Error", Messagebox.OK, Messagebox.ERROR)
        }
    }

    @Command
    fun delete(@BindingParam("item") movie: Movie) {
        movieRepository.delete(movie)
        Messagebox.show("Movie is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
    }

    @Command
    fun openLink() {
        Executions.getCurrent().sendRedirect(editingMovie.link, "_blank")
    }
}
