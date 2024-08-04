package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnResetting(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Resetting) return

        plugin.server.onlinePlayers.forEach { player ->
            player.inventory.clear()
        }
        plugin.gameManager.gameStateHandler.tryToChangeGameState(newGameState = GameState.Unset)
    }
}
