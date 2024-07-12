package de.megonno.cannibalkitchen.utils

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.bossbar.BossBar.Color
import net.kyori.adventure.bossbar.BossBar.Overlay
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class BossBarWorker(
    val plugin: CannibalKitchen,
    val player: Player,
    updateFrequency: Long,
    onCreate: BossBarWorker.() -> Unit = {},
    onUpdate: BossBarWorker.() -> Unit = {},
) {
    val bossBar = BossBar.bossBar(Component.empty(), 1f, Color.RED, Overlay.PROGRESS)

    init {
        player.showBossBar(bossBar)

        onCreate()

        plugin.server.scheduler.runTaskTimerAsynchronously(plugin, Runnable {
            onUpdate()
        }, 0L, updateFrequency * 20L)
    }

    fun hide() {
        player.hideBossBar(bossBar)
    }
}
