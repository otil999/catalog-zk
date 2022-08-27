package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Game
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.repository.GameRepository
import org.zkoss.bind.annotation.*
import org.zkoss.zk.ui.event.InputEvent
import org.zkoss.zul.Messagebox


class GameListViewModel : EntityManagerViewModel() {
    private val gameRepository: GameRepository by lazy { GameRepository(entityManager) }

    var games = mutableListOf<Game>()
    var action = ""
    var searchText = ""

    @GlobalCommand
    @NotifyChange("games", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun listGames(@BindingParam("action") action: String?, @BindingParam series: Series?) {
        this.action = action ?: ""
        searchText = ""
        games = when (action) {
            "randomLeastPlayed" -> gameRepository.randomLeastPlayed()
            "randomPlayed" -> gameRepository.randomPlayed()
            "series" -> gameRepository.findBySeries(series ?: Series())
            "recentlyPlayed" -> gameRepository.recentlyPlayed()
            else -> gameRepository.findAll()
        }.toMutableList()
    }

    @Command
    @NotifyChange("games")
    fun delete(@BindingParam("item") game: Game) {
        gameRepository.delete(game)
        games.remove(game)
        Messagebox.show("Game is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
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

    @GlobalCommand
    @NotifyChange("games", "refreshVisible", "searchVisible", "searchText", "editing", "storages")
    fun statRecentlyPlayedGames() = listGames("recentlyPlayed", null)
}
