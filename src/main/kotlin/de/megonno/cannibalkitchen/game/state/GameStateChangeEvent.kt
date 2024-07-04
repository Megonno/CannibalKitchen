package de.megonno.cannibalkitchen.game.state

import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GameStateChangeEvent(val oldGameState: GameState, val newGameState: GameState) : Event(), Cancellable {
    companion object {
        private val handler = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handler
    }

    private var cancelled = false

    override fun getHandlers(): HandlerList = handler

    override fun isCancelled(): Boolean = cancelled

    override fun setCancelled(cancel: Boolean) {
        cancelled = cancel
    }
}
