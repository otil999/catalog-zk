package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Game
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.repository.GameRepository
import hu.otilsoft.catalog.repository.MediaRepository
import hu.otilsoft.catalog.repository.SeriesRepository
import hu.otilsoft.catalog.repository.StorageRepository
import org.zkoss.bind.annotation.*
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zul.Messagebox


class GameViewModel : EntityManagerViewModel() {
    private val gameRepository: GameRepository by lazy { GameRepository(entityManager) }
    private val mediaRepository: MediaRepository by lazy { MediaRepository(entityManager) }
    private val seriesRepository: SeriesRepository by lazy { SeriesRepository(entityManager) }
    private val storageRepository: StorageRepository by lazy { StorageRepository(entityManager) }

    var editingGame = Game()
    var editing = false
    var games = mutableListOf<Game>()
    var media = mediaRepository.findAll()
    var series = mutableListOf<Series>()
    var action = ""
    var searchText = ""
    var storages = mutableListOf<Storage>()

    @GlobalCommand
    @NotifyChange("games", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun showGame(@BindingParam("action") action: String?, @BindingParam series: Series?) {
        this.action = action ?: ""
        searchText = ""
        editing = false
        games = when (action) {
            "randomLeastPlayed" -> gameRepository.randomLeastPlayed()
            "randomPlayed" -> gameRepository.randomPlayed()
            "series" -> gameRepository.findBySeries(series ?: Series())
            else -> gameRepository.findAll()
        }.toMutableList()
        storages = storageRepository.findAll().toMutableList()
        reloadSeries()
    }

    private fun reloadSeries() {
        series = seriesRepository.findAll().toMutableList().apply { add(0, Series()) }
    }

    @Command
    @NotifyChange("editing", "games")
    fun save() {
        if (editingGame.series?.name?.isBlank() == true) {
            editingGame.series = null
        }
        try {
            gameRepository.save(editingGame)
            games.remove(editingGame)
            games.add(editingGame)
            Messagebox.show("Game is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
            cancelEditing()
        } catch (ex: Exception) {
            Messagebox.show("Error when saving game.", "Error", Messagebox.OK, Messagebox.ERROR)
        }
    }

    @Command
    @NotifyChange("games")
    fun delete(@BindingParam("item") game: Game) {
        gameRepository.delete(game)
        games.remove(game)
        Messagebox.show("Game is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        cancelEditing()
    }

    @Command
    @NotifyChange("editing", "editingGame")
    fun edit(@BindingParam("item") game: Game?) {
        editing = true
        editingGame = game ?: Game()
        reloadSeries()
    }

    @Command
    @NotifyChange("editing", "games")
    fun cancelEditing() {
        editing = false
    }

    @Command
    @NotifyChange("games")
    fun refresh() {
        games = gameRepository.randomLeastPlayed().toMutableList()
    }

    fun isRefreshVisible() = action.isNotBlank()

    fun isSearchVisible() = !isRefreshVisible()

    @Command
    @NotifyChange("games", "refreshVisible", "searchVisible")
    fun showSeriesItems(@BindingParam("series") series: Series) {
        games = gameRepository.findBySeries(series).toMutableList()
        action = ""
    }

    @Command
    @NotifyChange("games", "searchText")
    fun search(@BindingParam("event") event: InputEvent) {
        searchText = event.value
        games = gameRepository.findByName(searchText).toMutableList()
    }
}
