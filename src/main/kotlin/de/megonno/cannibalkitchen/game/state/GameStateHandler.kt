package de.megonno.cannibalkitchen.game.state

import de.megonno.cannibalkitchen.CannibalKitchen

class GameStateHandler(private val plugin: CannibalKitchen) {
    private var _gameState: GameState = GameState.Unset
    val gameState: GameState
        get() = _gameState

    fun tryToChangeGameState(newGameState: GameState): Boolean {
        val event = GameStateChangeEvent(oldGameState = _gameState, newGameState = newGameState)

        plugin.server.pluginManager.callEvent(event)

        return if (!event.isCancelled) {
            _gameState = newGameState
            true
        } else false
    }

    fun isGameState(vararg gameState: GameState): Boolean = gameState.contains(this.gameState)

    fun foreStop() {
        plugin.server.pluginManager.callEvent(ForceStopEvent())
    }
}
