package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import de.megonno.cannibalkitchen.register.Displays
import de.megonno.cannibalkitchen.register.Items
import de.megonno.cannibalkitchen.utils.CountDown
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.TitlePart
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class OnStarting(private val plugin: CannibalKitchen) : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Starting) return

        plugin.server.scheduler.runTask(plugin, Runnable {
            plugin.server.onlinePlayers.forEach { player ->
                for (i in (0..2) + (6..8)) {
                    player.inventory.setItem(i, Items.locked.invoke())
                }
                for (i in 9..35) {
                    player.inventory.setItem(i, Items.void.invoke())
                }
                player.inventory.setItem(4, Items.upgrader.invoke())

                Displays.gameScoreboard(player)
                Displays.orderBossBar(player)

                plugin.gameManager.teamHandler.addPlayerToRandomTeam(player)
            }

            CountDown(plugin = plugin, players = plugin.server.onlinePlayers.toList(), 30) { time, players ->
                when {
                    time % 10 == 0 && time > 0 -> {
                        players.forEach { player -> player.sendMessage(Component.text("Start in ${time}s")) }
                    }

                    time in 1..10 -> {
                        players.forEach { player ->
                            player.sendTitlePart(TitlePart.TITLE, Component.text("Start in ${time}s"))
                        }
                    }

                    time == 0 -> {
                        players.forEach { player ->
                            player.sendTitlePart(TitlePart.TITLE, Component.text("Start in 0s"))
                        }
                        plugin.gameManager.cropHandler.start()
                        plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Running)
                    }
                }
            }
        })
    }
}
