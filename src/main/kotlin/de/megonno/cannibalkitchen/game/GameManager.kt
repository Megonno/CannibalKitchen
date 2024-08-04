package de.megonno.cannibalkitchen.game

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.crops.CropHandler
import de.megonno.cannibalkitchen.game.listeners.General
import de.megonno.cannibalkitchen.game.listeners.OnResetting
import de.megonno.cannibalkitchen.game.listeners.OnRunning
import de.megonno.cannibalkitchen.game.listeners.OnStarting
import de.megonno.cannibalkitchen.game.listeners.OnStopping
import de.megonno.cannibalkitchen.game.order.OrderHandler
import de.megonno.cannibalkitchen.game.state.GameStateHandler
import de.megonno.cannibalkitchen.game.team.GameTeam
import de.megonno.cannibalkitchen.game.team.TeamHandler
import de.megonno.cannibalkitchen.game.upgrades.UpgradeHandler
import de.megonno.cannibalkitchen.utils.ActionBarTimer
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World

class GameManager(private val plugin: CannibalKitchen, val world: World) {
    val gameStateHandler = GameStateHandler(plugin = plugin)
    val teamHandler = TeamHandler(
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
    val orderHandler = OrderHandler(plugin = plugin)
    val coinHandler = CoinHandler()
    val cropHandler = CropHandler(
        plugin = plugin,
        crops = mutableListOf(
            Location(world, -93.0, -11.0, 38.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 37.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 36.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 35.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 34.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 33.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 32.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 31.0) to Material.WHEAT,
            Location(world, -93.0, -11.0, 30.0) to Material.WHEAT,
        )
    )
    val upgradeHandler = UpgradeHandler(plugin = plugin)
    val timer = ActionBarTimer(plugin = plugin, mode = ActionBarTimer.ActionBarTimerMode.CountUp) {
        plugin.server.onlinePlayers.toMutableList()
    }

    fun registerEventHandlers() {
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
