package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.Game
import org.zkoss.bind.annotation.BindingParam
import org.zkoss.bind.annotation.Command
import org.zkoss.bind.annotation.GlobalCommand
import org.zkoss.bind.annotation.NotifyChange
import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Window


class MainViewModel {
    var emptyVisible = true
    var storageTypeVisible = false
    var storageVisible = false
    var seriesVisible = false
    var movieListVisible = false
    var movieEditVisible = false
    var gameListVisible = false
    var gameEditVisible = false
    var statVisible = false
    var statRecentlyViewedVisible = false
    var statRecentlyPlayedVisible = false

    private fun disableAll() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
        statRecentlyPlayedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun showStorageType() {
        disableAll()
        storageTypeVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun showStorage() {
        disableAll()
        storageVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun showSeries() {
        disableAll()
        seriesVisible = true
    }

    @GlobalCommand
    @NotifyChange(
            "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
            "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun listMovies() {
        disableAll()
        movieListVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun editMovie() {
        disableAll()
        movieEditVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun showMovieByStorage() {
        listMovies()
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun listGames() {
        disableAll()
        gameListVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun editGame(@BindingParam("item") game: Game?) {
        disableAll()
        gameEditVisible = true
    }

    @Command
    fun showSettings() {
        val win = Executions.createComponents("/settings.zul", null, null) as Window
        win.doModal()
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun statNumberOfAssets() {
        disableAll()
        statVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun statRecentlyViewedMovies() {
        disableAll()
        statRecentlyViewedVisible = true
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible", "statRecentlyPlayedVisible"
    )
    fun statRecentlyPlayedGames() {
        disableAll()
        statRecentlyPlayedVisible = true
    }
}
