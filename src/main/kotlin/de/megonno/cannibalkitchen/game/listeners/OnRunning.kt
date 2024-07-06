package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class OnRunning(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Running) return

        plugin.server.onlinePlayers.forEach { player -> player.sendMessage("started running game state") }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChangeItemInHand(event: PlayerItemHeldEvent) {
        if (plugin.gameManager.gameStateHandler.gameState != GameState.Running) return

        if (!(3..5).contains(event.newSlot)) {
            event.isCancelled = true
        }
    }
}
