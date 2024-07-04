package de.megonno.cannibalkitchen.game.state

import de.megonno.cannibalkitchen.CannibalKitchen

class GameStateHandler(private val plugin: CannibalKitchen) {
    private var gameState: GameState = GameState.Unset

    fun tryToChangeGameState(newGameState: GameState): Boolean {
        val event = GameStateChangeEvent(oldGameState = gameState, newGameState = newGameState)

        plugin.server.pluginManager.callEvent(event)

        return if (!event.isCancelled) {
            gameState = newGameState
            true
        } else false
    }

    fun foreStop() {
        plugin.server.pluginManager.callEvent(ForceStopEvent())
    }
}
