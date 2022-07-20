package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Movie
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.repository.*
import org.zkoss.bind.annotation.*
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zul.Messagebox


class MovieListViewModel : EntityManagerViewModel() {
    private val movieRepository: MovieRepository by lazy { MovieRepository(entityManager) }

    var movies = mutableListOf<Movie>()
    var action = ""
    var searchText = ""

    @GlobalCommand
    @NotifyChange("movies", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun listMovies(@BindingParam("action") action: String?, @BindingParam("series") series: Series?) {
        this.action = action ?: ""
        searchText = ""
        movies = when (action) {
            "series" -> movieRepository.findBySeries(series ?: Series())
            "randomLeastSeen" -> movieRepository.randomLeastSeen()
            "recentlyViewed" -> movieRepository.recentlyViewed()
            else -> movieRepository.findAll()
        }.toMutableList()
    }

    @GlobalCommand
    @NotifyChange("movies", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun statRecentlyViewedMovies() = listMovies("recentlyViewed", null)

    @GlobalCommand
    @NotifyChange("movies", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun showMovieByStorage(@BindingParam("storage") storage: Storage) {
        searchText = ""
        movies = movieRepository.findByStorage(storage).toMutableList()
    }

    @Command
    @NotifyChange("movies")
    fun refresh() {
        movies = movieRepository.randomLeastSeen().toMutableList()
    }

    fun isRefreshVisible() = action.isNotBlank()

    fun isSearchVisible() = !isRefreshVisible()

    @Command
    @NotifyChange("movies")
    fun delete(@BindingParam("item") movie: Movie) {
        movieRepository.delete(movie)
        movies.remove(movie)
        Messagebox.show("Movie is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
    }

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
