package de.megonno.cannibalkitchen.game

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.listeners.General
import de.megonno.cannibalkitchen.game.listeners.OnResetting
import de.megonno.cannibalkitchen.game.listeners.OnRunning
import de.megonno.cannibalkitchen.game.listeners.OnStarting
import de.megonno.cannibalkitchen.game.listeners.OnStopping
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateHandler
import de.megonno.cannibalkitchen.game.team.GameTeam
import de.megonno.cannibalkitchen.game.team.TeamHandler
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.World

class GameManager(private val plugin: CannibalKitchen, val world: World) {
    private val gameStateHandler = GameStateHandler(plugin = plugin)
    private val teamHandler = TeamHandler(
        teamList = listOf(
            GameTeam(
                name = Component.text("Team 1"),
                spawningArea = Location(world, 0.0, 0.0, 0.0) to Location(world, 0.0, 0.0, 0.0)
            ), GameTeam(
                name = Component.text("Team 2"),
                spawningArea = Location(world, 0.0, 0.0, 0.0) to Location(world, 0.0, 0.0, 0.0)
            ), GameTeam(
                name = Component.text("Team 3"),
                spawningArea = Location(world, 0.0, 0.0, 0.0) to Location(world, 0.0, 0.0, 0.0)
            ), GameTeam(
                name = Component.text("Team 4"),
                spawningArea = Location(world, 0.0, 0.0, 0.0) to Location(world, 0.0, 0.0, 0.0)
            )
        )
    )

    fun startGame(): Boolean = gameStateHandler.tryToChangeGameState(GameState.Starting)

    fun stopGame(): Boolean = gameStateHandler.tryToChangeGameState(GameState.Stopping)

    fun registerEventHandlers(plugin: CannibalKitchen) {
        listOf(
            General(),
            OnResetting(plugin = plugin),
            OnRunning(plugin = plugin),
            OnStarting(plugin = plugin),
            OnStopping(plugin = plugin)
        ).forEach { handler ->
            plugin.server.pluginManager.registerEvents(handler, plugin)
        }
    }
}
