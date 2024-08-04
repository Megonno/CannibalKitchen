package de.megonno.cannibalkitchen.displays

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.bossbar.BossBar.Color
import net.kyori.adventure.bossbar.BossBar.Overlay
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class BossBarWorker(
    val plugin: CannibalKitchen,
    val player: Player,
    updateFrequency: Long,
    onCreate: BossBarWorker.() -> Unit = {},
    onUpdate: BossBarWorker.() -> Unit = {},
) {
    private lateinit var task: BukkitTask
    val bossBar = BossBar.bossBar(Component.empty(), 1f, Color.RED, Overlay.PROGRESS)

    init {
        if (plugin.bossBarWorkers[player.uniqueId] != null) {
            throw IllegalStateException("There can not be two BossBarWorker for one Player!")
        }

        player.showBossBar(bossBar)
        onCreate()

        task = plugin.server.scheduler.runTaskTimerAsynchronously(plugin, Runnable {
            if (player.isOnline) onUpdate() else task.cancel()
        }, 0L, updateFrequency * 20L)

        plugin.bossBarWorkers[player.uniqueId] = this
    }

    fun destroy() {
        hide()
        plugin.bossBarWorkers.remove(player.uniqueId)
    }

    fun hide() {
        player.hideBossBar(bossBar)
    }
}
