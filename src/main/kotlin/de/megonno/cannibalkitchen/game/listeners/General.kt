package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class General : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState == event.oldGameState) return
    }
}
