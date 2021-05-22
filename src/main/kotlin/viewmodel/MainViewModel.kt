package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.CatalogSessionListener
import hu.otilsoft.catalog.Media
import hu.otilsoft.catalog.Movie
import hu.otilsoft.catalog.repository.MediaRepository
import hu.otilsoft.catalog.repository.MovieRepository
import hu.otilsoft.catalog.repository.SeriesRepository
import java.util.ArrayList
import javax.persistence.EntityManager
import javax.servlet.http.HttpSession
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zk.ui.Sessions


class MainViewModel {
    private val mediaRepository: MediaRepository
    private val seriesRepository: SeriesRepository
    private val movieRepository: MovieRepository

    var visibleMedia = false
    var visibleGenre = false
    var visibleMovie = false

//        get() = mediaRepository.findAll()

    //    var media
//        private set
//    var movies: List<Movie> = ArrayList()
//        private set
//    var editingMovie = Movie()
//
//    private fun reloadMedia() {
//        media = mediaRepository.findAll()
//    }
//
//    val storages: List<Media>

    init {
        val httpSession = Sessions.getCurrent().nativeSession as HttpSession
        val entityManager = httpSession.getAttribute(CatalogSessionListener.SESSION_ATTRIBUTE_NAME) as EntityManager
        mediaRepository = MediaRepository(entityManager)
        seriesRepository = SeriesRepository(entityManager)
        movieRepository = MovieRepository(entityManager)
    }

    @Command
    @NotifyChange("visibleMedia", "visibleGenre", "visibleMovie", "editing", "media")
    fun enableMedia() {
        visibleMedia = true
        visibleGenre = false
        visibleMovie = false
    }

    @Command
    @NotifyChange("visibleMedia", "visibleGenre", "visibleMovie", "editing")
    fun enableGenre() {
        visibleMedia = false
        visibleGenre = true
        visibleMovie = false
    }

    @Command
    @NotifyChange("visibleMedia", "visibleGenre", "visibleMovie", "editing", "movies")
    fun enableMovie() {
        visibleMedia = false
        visibleGenre = false
        visibleMovie = true
    }

    @Command
    @NotifyChange("visibleMedia", "visibleGenre", "visibleMovie", "editing", "movies")
    fun randomLeastSeenMovie() {
//        movies = movieRepository.randomLeastSeen()
        visibleMedia = false
        visibleGenre = false
        visibleMovie = true
    }
}
