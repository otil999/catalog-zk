package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.Movie
import hu.otilsoft.catalog.Series
import hu.otilsoft.catalog.repository.MediaRepository
import hu.otilsoft.catalog.repository.MovieRepository
import hu.otilsoft.catalog.repository.SeriesRepository
import hu.otilsoft.catalog.repository.StorageRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.Init
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.bind.annotation.QueryParam
import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Messagebox


class MovieViewModel : EntityManagerViewModel() {
    private val movieRepository = MovieRepository(entityManager)
    private val mediaRepository = MediaRepository(entityManager)
    private val seriesRepository = SeriesRepository(entityManager)
    private val storageRepository = StorageRepository(entityManager)
    var editingMovie = Movie()
    var editing = false
    var movies = mutableListOf<Movie>()
    val media = mediaRepository.findAll()
    val series = seriesRepository.findAll().toMutableList().apply { add(0, Series()) }.toMutableList()
    val storages = storageRepository.findAll()

    @Init
    fun init(@QueryParam("action") action: String?) {
        movies = (if ("randomLeastSeen" == action) movieRepository.randomLeastSeen() else movieRepository.findAll()).toMutableList()
    }

    @Command
    @NotifyChange("editing", "editingMovie")
    fun edit(@BindingParam("item") movie: Movie?) {
        editing = true
        editingMovie = movie ?: Movie()
    }

    @Command
    @NotifyChange("editing", "movies")
    fun cancelEditing() {
        editing = false
    }

    @Command
    @NotifyChange("editing", "movies")
    fun save() {
        movieRepository.save(editingMovie)
        movies.add(editingMovie)
        Messagebox.show("Movie is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        cancelEditing()
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
    @NotifyChange("editing", "editingMovie", "movies")
    fun deleteEditing() {
        delete(editingMovie)
    }

    @Command
    fun openLink() {
        Executions.getCurrent().sendRedirect(editingMovie.link, "_blank")
    }
}
