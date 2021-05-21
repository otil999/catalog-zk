package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.model.Game;
import hu.otilsoft.catalog.model.Media;
import hu.otilsoft.catalog.model.Movie;
import hu.otilsoft.catalog.model.Series;
import hu.otilsoft.catalog.repository.GameRepository;
import hu.otilsoft.catalog.repository.MediaRepository;
import hu.otilsoft.catalog.repository.MovieRepository;
import hu.otilsoft.catalog.repository.SeriesRepository;
import org.zkoss.bind.annotation.*;

import java.util.List;

public class GameViewModel extends EntityManagerViewModel {
    private final GameRepository gameRepository;
    private final MediaRepository mediaRepository;
    private final SeriesRepository seriesRepository;
    private Game editingGame;
    private boolean editing;
    private String action;

    public GameViewModel() {
        gameRepository = new GameRepository(entityManager);
        mediaRepository = new MediaRepository(entityManager);
        seriesRepository = new SeriesRepository(entityManager);
    }

    @Init
    public void init(@QueryParam("action") String action) {
        this.action = action;
    }

    public List<Game> getGames() {
        return "leastPlayed".equals(action) ? gameRepository.randomLeastSeen() : gameRepository.findAll();
    }

    public List<Media> getMedia() { return mediaRepository.findAll(); }

    public List<Series> getSeries() { return seriesRepository.findAll(); }

    @Command
    @NotifyChange({"editing", "editingGame"})
    public void edit(@BindingParam("item") Game game) {
        editing = true;
        editingGame = game;
        if (game.getSeries() == null) {
            editingGame.setSeries(Series.EMPTY);
        }
    }

    @Command
    @NotifyChange({"editing", "games"})
    public void cancelEditing() {
        editing = false;
        getGames();
    }

    public boolean isEditing() {
        return editing;
    }

    public Game getEditingGame() {
        return editingGame;
    }
}
