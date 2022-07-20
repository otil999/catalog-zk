package hu.otilsoft.catalog.viewmodel

import hu.otilsoft.catalog.model.CountHistory
import org.zkoss.bind.annotation.GlobalCommand
import org.zkoss.zk.ui.util.Clients


class StatViewModel : EntityManagerViewModel() {
    @GlobalCommand
    fun statNumberOfAssets() {
        val resultList = entityManager.createQuery(
            "select counter from CountHistory counter order by date",
            CountHistory::class.java
        ).resultList
        var tableData = "[['Date', 'Number of Movies', 'Number of Games'],"
        for (counter in resultList) {
            tableData += "['${counter.date}', ${counter.activeMovies}, ${counter.activeGames}],"
        }
        tableData += "]"
        Clients.evalJavaScript("drawChart($tableData);")
    }
}
