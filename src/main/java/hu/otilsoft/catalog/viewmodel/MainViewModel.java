package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.CatalogSessionListener;
import hu.otilsoft.catalog.model.Media;
import hu.otilsoft.catalog.model.Movie;
import hu.otilsoft.catalog.model.Series;
import hu.otilsoft.catalog.repository.MediaRepository;
import hu.otilsoft.catalog.repository.MovieRepository;
import hu.otilsoft.catalog.repository.SeriesRepository;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Sessions;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel {
    private final MediaRepository mediaRepository;
    private final SeriesRepository seriesRepository;
    private final MovieRepository movieRepository;
    private boolean visibleMedia;
    private boolean visibleGenre;
    private boolean visibleMovie;
    private List<Media> media = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private Movie editingMovie = new Movie();

    public MainViewModel() {
        HttpSession httpSession = (HttpSession) Sessions.getCurrent().getNativeSession();
        EntityManager entityManager = (EntityManager) httpSession.getAttribute(CatalogSessionListener.SESSION_ATTRIBUTE_NAME);
        mediaRepository = new MediaRepository(entityManager);
        seriesRepository = new SeriesRepository(entityManager);
        movieRepository = new MovieRepository(entityManager);
    }

    public boolean isVisibleMedia() {
        return visibleMedia;
    }

    public boolean isVisibleGenre() {
        return visibleGenre;
    }

    public boolean isVisibleMovie() {
        return visibleMovie;
    }

    public List<Media> getMedia() {
        return media;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    private void reloadMedia() {
        media = mediaRepository.findAll();
    }

    public Movie getEditingMovie() {
        return editingMovie;
    }

    public void setEditingMovie(Movie editingMovie) {
        this.editingMovie = editingMovie;
    }

    public List<Media> getStorages() {
        return mediaRepository.findAll();
    }

    @Command
    @NotifyChange({"visibleMedia", "visibleGenre", "visibleMovie", "editing", "media"})
    public void enableMedia() {
        visibleMedia = true;
        visibleGenre = false;
        visibleMovie = false;
        reloadMedia();
    }

    @Command
    @NotifyChange({"visibleMedia", "visibleGenre", "visibleMovie", "editing"})
    public void enableGenre() {
        visibleMedia = false;
        visibleGenre = true;
        visibleMovie = false;
    }

    @Command
    @NotifyChange({"visibleMedia", "visibleGenre", "visibleMovie", "editing", "movies"})
    public void enableMovie() {
        visibleMedia = false;
        visibleGenre = false;
        visibleMovie = true;
    }

    @Command
    @NotifyChange({"visibleMedia", "visibleGenre", "visibleMovie", "editing", "movies"})
    public void randomLeastSeenMovie() {
        movies = movieRepository.randomLeastSeen();
        visibleMedia = false;
        visibleGenre = false;
        visibleMovie = true;
    }
}
