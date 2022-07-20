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

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun showStorageType() {
        emptyVisible = false
        storageTypeVisible = true
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun showStorage() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = true
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun showSeries() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = true
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun listMovies() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = true
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun editMovie() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = true
        gameListVisible = false
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun showMovieByStorage() {
        listMovies()
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun listGames() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = true
        gameEditVisible = false
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun editGame(@BindingParam("item") game: Game?) {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = true
        statVisible = false
        statRecentlyViewedVisible = false
    }

    @Command
    fun showSettings() {
        val win = Executions.createComponents("/settings.zul", null, null) as Window
        win.doModal()
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun statNumberOfAssets() {
        emptyVisible = false
        storageTypeVisible = false
        storageVisible = false
        seriesVisible = false
        movieListVisible = false
        movieEditVisible = false
        gameListVisible = false
        gameEditVisible = false
        statVisible = true
        statRecentlyViewedVisible = false
    }

    @GlobalCommand
    @NotifyChange(
        "emptyVisible", "storageTypeVisible", "storageVisible", "seriesVisible", "movieListVisible", "movieEditVisible",
        "gameListVisible", "gameEditVisible", "statVisible", "statRecentlyViewedVisible"
    )
    fun statRecentlyViewedMovies() {
        listMovies()
        movieListVisible = false
        statRecentlyViewedVisible = true
    }
}
