package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnStopping(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Stopping) return

        val gameManager = plugin.gameManager

        gameManager.timer.pauseTimer()
        gameManager.cropHandler.stop()

        val winnerTeams = plugin.gameManager.orderHandler.winner()
            .mapNotNull { plugin.gameManager.teamHandler.getTeamById(uuid = it) }

        var winnerMessage = Component.text("Winner is ")
        winnerTeams.forEachIndexed { index, currentTeam ->
            winnerMessage = winnerMessage.append(currentTeam.name)
            if (winnerTeams.size - 2 > index) {
                winnerMessage = winnerMessage.append(Component.text(", "))
            } else if (winnerTeams.size - 2 == index) {
                winnerMessage = winnerMessage.append(Component.text(" & "))
            }
        }

        plugin.server.onlinePlayers.forEach { player ->
            player.showTitle(Title.title(winnerMessage, Component.empty()))
        }
    }
}
