package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import de.megonno.cannibalkitchen.register.Items
import de.megonno.cannibalkitchen.utils.CountDown
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.TitlePart
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnStarting(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Starting) return

        plugin.server.scheduler.runTask(plugin, Runnable {
            plugin.server.onlinePlayers.forEach { player ->
                player.inventory.addItem(Items.upgrader())

                CountDown(plugin = plugin, players = plugin.server.onlinePlayers.toList(), 60) { time, players ->
                    when {
                        time % 10 == 0 && time > 0 -> {
                            players.forEach { player -> player.sendMessage(Component.text("Start in ${time}s")) }
                        }

                        time in 1..10 -> {
                            players.forEach { player -> player.sendTitlePart(TitlePart.TITLE, Component.text("Start in ${time}s")) }
                        }

                        time == 0 -> {
                            players.forEach { player -> player.sendTitlePart(TitlePart.TITLE, Component.text("Start in ${time}s")) }
                            plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Running)
                        }
                    }
                }
            }
        })
    }
}