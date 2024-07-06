package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnStopping(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Stopping) return

        plugin.gameManager.timer.pauseTimer()
    }
}
