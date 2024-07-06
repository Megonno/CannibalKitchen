package de.megonno.cannibalkitchen.utils

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import kotlin.math.abs

class ActionBarTimer(private val plugin: CannibalKitchen, private val players: List<Player>) {
    private var time = 0
    private var task: BukkitTask? = null

    fun startTimer() {
        task = plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            updateTimer()
            time++
        }, 0L, 20L)
    }

    fun pauseTimer() {
        task?.cancel()
    }

    private fun updateTimer() {
        val hours = abs(time / 3600)
        val minutes = abs((time % 3600) / 60)
        val seconds = abs(time % 60)

        var content = ""
        if (hours != 0) content += "${hours}h "
        if (minutes != 0 || hours != 0) content += "${minutes}m "
        if (time != 0) content += "${seconds}s"

        players.forEach { player ->
            if (content.isNotEmpty()) {
                player.sendActionBar(
                    Component.text(content).decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                )
            }
        }
    }
}
