package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnResetting(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Resetting) return
        if (event.newGameState == event.oldGameState) return
    }
}
