package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.Game
import hu.otilsoft.catalog.repository.GameRepository
import hu.otilsoft.catalog.repository.MediaRepository
import hu.otilsoft.catalog.repository.SeriesRepository
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.Init
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.bind.annotation.QueryParam


class GameViewModel : EntityManagerViewModel() {
    private val gameRepository = GameRepository(entityManager)
    private val mediaRepository = MediaRepository(entityManager)
    private val seriesRepository = SeriesRepository(entityManager)
    var editingGame = Game()
    var editing = false
    var games = mutableListOf<Game>()
    var media = mediaRepository.findAll()
    var series = seriesRepository.findAll()

    @Init
    fun init(@QueryParam("action") action: String?) {
        games = (if ("leastPlayed" == action) gameRepository.randomLeastSeen() else gameRepository.findAll()).toMutableList()
    }

    @Command
    @NotifyChange("editing", "editingGame")
    fun edit(@BindingParam("item") game: Game?) {
        editing = true
        editingGame = game ?: Game()
    }

    @Command
    @NotifyChange("editing", "games")
    fun cancelEditing() {
        editing = false
    }
}
