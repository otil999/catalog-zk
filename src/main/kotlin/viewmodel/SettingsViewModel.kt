package hu.otilsoft.catalog.viewmodel

import org.zkoss.bind.annotation.Command
import org.zkoss.zk.ui.Executions
import org.zkoss.zul.Window


class SettingsViewModel {
    @Command
    fun detach() {
        val win = Executions.getCurrent().desktop.firstPage.getFellow("settingsWin") as Window
        win.detach()
    }
}
