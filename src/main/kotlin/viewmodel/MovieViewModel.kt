package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Movie
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.repository.*
import org.zkoss.bind.annotation.*
import org.zkoss.zk.ui.Executions
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zul.Messagebox


class MovieViewModel : EntityManagerViewModel() {
    private val movieRepository: MovieRepository by lazy { MovieRepository(entityManager) }
    private val mediaRepository: MediaRepository by lazy { MediaRepository(entityManager) }
    private val seriesRepository: SeriesRepository by lazy { SeriesRepository(entityManager) }
    private val storageRepository: StorageRepository by lazy { StorageRepository(entityManager) }
    var editingMovie = Movie()
    var editing = false
    var movies = mutableListOf<Movie>()
    val media = mediaRepository.findAll()
    var series = mutableListOf<Series>()
    var storages = mutableListOf<Storage>()
    var action = ""
    var searchText = ""

    @GlobalCommand
    @NotifyChange("movies", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun showMovie(@BindingParam("action") action: String?, @BindingParam("series") series: Series?) {
        this.action = action ?: ""
        searchText = ""
        editing = false
        movies = when (action) {
            "series" -> movieRepository.findBySeries(series ?: Series())
            "randomLeastSeen" -> movieRepository.randomLeastSeen()
            else -> movieRepository.findAll()
        }.toMutableList()
        storages = storageRepository.findAll().toMutableList()
        reloadSeries()
    }

    @GlobalCommand
    @NotifyChange("movies", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun showMovieByStorage(@BindingParam("storage") storage: Storage) {
        searchText = ""
        editing = false
        movies = movieRepository.findByStorage(storage).toMutableList()
        storages = storageRepository.findAll().toMutableList()
        reloadSeries()
    }

    @Command
    @NotifyChange("editing", "editingMovie")
    fun edit(@BindingParam("item") movie: Movie?) {
        editing = true
        editingMovie = movie ?: Movie()
        reloadSeries()
    }

    private fun reloadSeries() {
        series = seriesRepository.findAll().toMutableList().apply { add(0, Series()) }
    }

    @Command
    @NotifyChange("editing", "movies")
    fun cancelEditing() {
        editing = false
    }

    @Command
    @NotifyChange("editing", "movies")
    fun save() {
        if (editingMovie.series?.name?.isBlank() == true) {
            editingMovie.series = null
        }
        try {
            movieRepository.save(editingMovie)
            Messagebox.show("Movie is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        } catch (ex: Exception) {
            Messagebox.show("Error when saving movie.", "Error", Messagebox.OK, Messagebox.ERROR)
        }
    }

    @Command
    @NotifyChange("movies")
    fun delete(@BindingParam("item") movie: Movie) {
        movieRepository.delete(movie)
        movies.remove(movie)
        Messagebox.show("Movie is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        cancelEditing()
    }

    @Command
    @NotifyChange("editing", "movies")
    fun deleteEditing() {
        delete(editingMovie)
    }

    @Command
    fun openLink() {
        Executions.getCurrent().sendRedirect(editingMovie.link, "_blank")
    }

    @Command
    @NotifyChange("movies")
    fun refresh() {
        movies = movieRepository.randomLeastSeen().toMutableList()
    }

    fun isRefreshVisible() = action.isNotBlank()

    fun isSearchVisible() = !isRefreshVisible()

    @Command
    @NotifyChange("movies", "refreshVisible", "searchVisible")
    fun showSeriesItems(@BindingParam("series") series: Series) {
        movies = movieRepository.findBySeries(series).toMutableList()
        action = ""
    }

    @Command
    @NotifyChange("movies", "searchText")
    fun search(@BindingParam("event") event: InputEvent) {
        searchText = event.value
        movies = movieRepository.findByName(searchText).toMutableList()
    }

    val ascComparator = Comparator<Movie> { movie1, movie2 -> movieRepository.collator.compare(movie1.name, movie2.name) }
    val descComparator = Comparator<Movie> { movie1, movie2 -> movieRepository.collator.compare(movie2.name, movie1.name) }
}
