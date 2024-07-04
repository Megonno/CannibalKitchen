package de.megonno.cannibalkitchen.game.state

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class ForceStopEvent : Event() {
    companion object {
        private val handler = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList = handler
    }

    private var cancelled = false

    override fun getHandlers(): HandlerList = handler
}
