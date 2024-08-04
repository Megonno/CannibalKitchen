package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.displays.BossBarWorker
import de.megonno.cannibalkitchen.displays.ScoreboardWorker
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

object Displays {
    private lateinit var plugin: CannibalKitchen

    val gameScoreboard = { player: Player ->
        ScoreboardWorker(plugin = plugin, player = player, registeredName = "game", updateFrequency = 1) {
            displayName(Component.text(" Cannibal Kitchen ").color(NamedTextColor.AQUA).decoration(TextDecoration.BOLD, true))

            setLine(7, Component.empty())
            setLine(6, Component.text("GameState: ${plugin.gameManager.gameStateHandler.gameState.javaClass.simpleName}"))
            setLine(5, Component.empty())
            setLine(4, Component.text("Team:").color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.BOLD, true))
            setLine(3, (plugin.gameManager.teamHandler.getTeamByPlayer(player)?.name ?: Component.text("undefined")).color(NamedTextColor.GREEN))
            setLine(2, Component.empty())
            setLine(1, Component.text("Coins").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true))
            setLine(0, Component.text(plugin.gameManager.coinHandler.coins[player.uniqueId] ?: 0).color(NamedTextColor.YELLOW))
        }
    }

    val orderBossBar = { player: Player ->
        BossBarWorker(plugin = plugin, player = player, updateFrequency = 1) {
            val gameManager = plugin.gameManager
            val teamId = gameManager.teamHandler.getTeamIdByPlayer(player)

            if (teamId != null) {
                val order = gameManager.orderHandler.getCurrentOrder(teamId)

                var content = Component.empty()

                for ((i, item) in order.items.withIndex()) {
                    if ((1..<order.items.size).contains(i) && item.amount >= 1) {
                        content = content.append(Component.text(", "))
                    }

                    if (item.amount >= 1) {
                        content = content.append(Component.text("${item.amount}x "))
                        content = content.append(item.itemMeta.displayName()?: Component.text(item.type.name))
                    }
                }

                bossBar.name(content)
            }
        }
    }

    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin
    }
}
