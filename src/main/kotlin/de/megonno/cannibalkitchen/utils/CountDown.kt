package de.megonno.cannibalkitchen.utils

import de.megonno.cannibalkitchen.CannibalKitchen
import org.bukkit.entity.Player

class CountDown(
    plugin: CannibalKitchen,
    players: List<Player>,
    private var time: Int,
    listener: (Int, List<Player>) -> Unit
) {
    private val task = plugin.server.scheduler.runTaskTimer(plugin, Runnable {
        listener(time, players)
        if (time > 0) time-- else cancelTimer()
    }, 0L, 20L)

    private fun cancelTimer() {
        task.cancel()
    }
}
