package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.utils.MScoreboard
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

object Scoreboards {
    private lateinit var plugin: CannibalKitchen

    val gameScoreboard = { player: Player ->
        MScoreboard(plugin = plugin, player = player, registeredName = "game", updateFrequency = 1) {
            displayName(Component.text(" Cannibal Kitchen ").color(NamedTextColor.AQUA).decoration(TextDecoration.BOLD, true))

            setLine(5, Component.empty())
            setLine(4, Component.text("Team:").color(NamedTextColor.DARK_GREEN).decoration(TextDecoration.BOLD, true))
            setLine(3, (plugin.gameManager.teamHandler.teams[player.uniqueId]?.name ?: Component.text("undefined")).color(NamedTextColor.GREEN))
            setLine(2, Component.empty())
            setLine(1, Component.text("Coins").color(NamedTextColor.GOLD).decoration(TextDecoration.BOLD, true))
            setLine(0, Component.text(plugin.gameManager.coinHandler.coins[player.uniqueId] ?: 0).color(NamedTextColor.YELLOW))
        }
    }

    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin
    }
}
