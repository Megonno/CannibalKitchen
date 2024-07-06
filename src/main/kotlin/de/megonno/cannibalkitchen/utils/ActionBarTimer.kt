package de.megonno.cannibalkitchen.utils

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import kotlin.math.abs

class ActionBarTimer(private val plugin: CannibalKitchen, private val players: () -> MutableList<Player>) {
    private var time = 0
    private var paused = true

    init {
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            updateTimer()
            if (!paused) time++
        }, 0L, 20L)
    }

    fun startTimer() {
        paused = false
    }

    fun pauseTimer() {
        paused = true
    }

    private fun updateTimer() {
        val hours = abs(time / 3600)
        val minutes = abs((time % 3600) / 60)
        val seconds = abs(time % 60)

        var content = ""
        if (hours != 0) content += "${hours}h "
        if (minutes != 0 || hours != 0) content += "${minutes}m "
        if (time != 0) content += "${seconds}s"

        players().forEach { player ->
            if (paused) {
                player.sendActionBar(
                    Component.text("-paused-").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                )
            } else if (content.isNotEmpty()) {
                player.sendActionBar(
                    Component.text(content).decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                )
            }
        }
    }
}
