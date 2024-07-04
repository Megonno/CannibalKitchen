package de.megonno.cannibalkitchen.game.state

sealed class GameState {
    data object Unset : GameState()
    data object Starting : GameState()
    data object Running : GameState()
    data object Stopping : GameState()
    data object Resetting : GameState()
}
