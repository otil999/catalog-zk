package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.model.*;
import hu.otilsoft.catalog.repository.MediaRepository;
import hu.otilsoft.catalog.repository.MovieRepository;
import hu.otilsoft.catalog.repository.SeriesRepository;
import hu.otilsoft.catalog.repository.StorageRepository;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import java.util.List;

@SuppressWarnings("unused")
public class MovieViewModel extends EntityManagerViewModel {
    private final MovieRepository movieRepository;
    private final MediaRepository mediaRepository;
    private final SeriesRepository seriesRepository;
    private final StorageRepository storageRepository;
    private Movie editingMovie;
    private boolean editing;
    private String action;

    public MovieViewModel() {
        movieRepository = new MovieRepository(entityManager);
        mediaRepository = new MediaRepository(entityManager);
        seriesRepository = new SeriesRepository(entityManager);
        storageRepository = new StorageRepository(entityManager);
    }

    @Init
    public void init(@QueryParam("action") String action) {
        this.action = action;
    }

    public List<Movie> getMovies() {
        return "randomLeastSeen".equals(action) ? movieRepository.randomLeastSeen() : movieRepository.findAll();
    }

    public List<Media> getMedia() { return mediaRepository.findAll(); }

    public List<Series> getSeries() {
        List<Series> series = seriesRepository.findAll();
        series.add(0, Series.EMPTY);
        return series;
    }

    public List<Storage> getStorages() {
        return storageRepository.findAll();
    }

    @Command
    @NotifyChange({"editing", "editingMovie"})
    public void edit(@BindingParam("item") Movie movie) {
        editing = true;
        editingMovie = movie;
        if (movie.getSeries() == null) {
            editingMovie.setSeries(Series.EMPTY);
        }
    }

    @Command
    @NotifyChange({"editing", "movies"})
    public void cancelEditing() {
        editing = false;
        getMovies();
    }

    @Command
    @NotifyChange({"editing", "editingMovie"})
    public void save() {
        movieRepository.save(editingMovie);
        Messagebox.show("Movie is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION);
    }

    @Command
    @NotifyChange({"movies"})
    public void delete(@BindingParam("item") Movie movie) {
        movieRepository.delete(movie);
        editing = false;
        editingMovie = null;
        Messagebox.show("Movie is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION);
    }

    @Command
    @NotifyChange({"editing", "editingMovie", "movies"})
    public void deleteEditing() {
        delete(editingMovie);
    }

    @Command
    public void openLink() {
        Executions.getCurrent().sendRedirect(editingMovie.getLink(), "_blank");
    }

    public boolean isEditing() {
        return editing;
    }

    public Movie getEditingMovie() {
        return editingMovie;
    }
}
