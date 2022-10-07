package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Game
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.repository.GameRepository
import hu.otilsoft.catalog.repository.SeriesRepository
import hu.otilsoft.catalog.repository.StorageRepository
import org.zkoss.bind.annotation.*
import org.zkoss.zul.Messagebox
import java.time.LocalDateTime


class GameEditViewModel : EntityManagerViewModel() {
    private val gameRepository: GameRepository by lazy { GameRepository(entityManager) }
    private val seriesRepository: SeriesRepository by lazy { SeriesRepository(entityManager) }
    private val storageRepository: StorageRepository by lazy { StorageRepository(entityManager) }

    var editingGame = Game()
    var series = mutableListOf<Series>()
    var storages = mutableListOf<Storage>()

    private var affectedSeries: Series? = null
    private var oldCounter: Int? = null

    @GlobalCommand
    @NotifyChange("editingGame", "series", "storages")
    fun editGame(@BindingParam("item") game: Game?) {
        storages = storageRepository.findAll().toMutableList()
        series = seriesRepository.findAll().toMutableList().apply { add(0, Series()) }
        editingGame = game ?: Game()
        affectedSeries = editingGame.series
        oldCounter = game?.counter
    }

    @Command
    fun save() {
        if (editingGame.series?.name?.isBlank() == true) {
            editingGame.series = null
        }
        if (editingGame.counter != oldCounter) {
            editingGame.lastPlayed = LocalDateTime.now()
        }
        try {
            gameRepository.save(editingGame, affectedSeries)
            Messagebox.show("Game is saved successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
        } catch (ex: Exception) {
            Messagebox.show("Error when saving game.", "Error", Messagebox.OK, Messagebox.ERROR)
        }
    }

    @Command
    fun delete(@BindingParam("item") game: Game) {
        gameRepository.delete(game)
        Messagebox.show("Game is deleted successfully.", "Success", Messagebox.OK, Messagebox.INFORMATION)
    }
}
