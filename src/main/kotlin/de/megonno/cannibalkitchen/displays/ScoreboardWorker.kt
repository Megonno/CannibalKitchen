package de.megonno.cannibalkitchen.displays

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.Criteria
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Team

class ScoreboardWorker(
    val plugin: CannibalKitchen,
    val player: Player,
    val registeredName: String,
    updateFrequency: Long,
    onCreate: ScoreboardWorker.() -> Unit = {},
    onUpdate: ScoreboardWorker.() -> Unit = {},
) {
    private lateinit var task: BukkitTask
    private val scoreboard = plugin.server.scoreboardManager.newScoreboard
    private val objective = scoreboard.registerNewObjective(
        registeredName, Criteria.DUMMY, Component.text(registeredName), RenderType.INTEGER
    ).apply {
        displaySlot = DisplaySlot.SIDEBAR
    }

    init {
        if (plugin.scoreboardWorkers[player.uniqueId] != null) {
            throw IllegalStateException("There can not be two ScoreboardWorker for one Player!")
        }

        player.scoreboard = scoreboard
        onCreate()

        task = plugin.server.scheduler.runTaskTimerAsynchronously(plugin, Runnable {
            if (player.isOnline) onUpdate() else task.cancel()
        }, 0L, updateFrequency * 20L)

        plugin.scoreboardWorkers[player.uniqueId] = this
    }

    fun destroy() {
        player.scoreboard = plugin.server.scoreboardManager.mainScoreboard
        plugin.scoreboardWorkers.remove(player.uniqueId)
    }

    fun displayName(displayName: Component) {
        objective.displayName(displayName)
    }

    fun setLine(slot: Int, content: Component) {
        if (!checkSlot(slot)) return

        team(slot).apply {
            prefix(content)
            addEntry(entry(slot))
        }

        objective.getScore(entry(slot)).score = slot
    }

    fun removeLine(slot: Int) {
        if (!checkSlot(slot)) return

        team(slot).unregister()
        scoreboard.resetScores(entry(slot))
    }

    private fun checkSlot(slot: Int): Boolean = slot in 0..16

    private fun team(slot: Int): Team =
        scoreboard.getTeam("$registeredName:$slot") ?: scoreboard.registerNewTeam("$registeredName:$slot")

    private fun entry(slot: Int): String = "\u00A7" + (('0'..'9') + ('a'..'f'))[slot]
}
