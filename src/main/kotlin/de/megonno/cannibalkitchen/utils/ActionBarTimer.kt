package de.megonno.cannibalkitchen.utils

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import kotlin.math.abs

class ActionBarTimer(
    private val plugin: CannibalKitchen,
    private var mode: ActionBarTimerMode,
    private val players: () -> MutableList<Player>,
) {
    private var time = 0
    private var state: ActionBarTimerState = ActionBarTimerState.Unset

    init {
        plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            updateTimer()

            when (mode) {
                is ActionBarTimerMode.CountDown -> time++
                is ActionBarTimerMode.CountUp -> time--
            }
        }, 0L, 20L)
    }

    fun startTimer() {
        state = ActionBarTimerState.Running
    }

    fun pauseTimer() {
        state = ActionBarTimerState.Paused
    }

    fun unset() {
        state = ActionBarTimerState.Unset
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
            when (state) {
                ActionBarTimerState.Running -> {
                    player.sendActionBar(
                        Component.text(content).decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                    )
                }

                ActionBarTimerState.Paused -> {
                    player.sendActionBar(
                        Component.text("paused").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                    )
                }

                ActionBarTimerState.Unset -> {
                    player.sendActionBar(
                        Component.text("unset").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE)
                    )
                }
            }
        }
    }

    sealed class ActionBarTimerMode {
        data object CountDown : ActionBarTimerMode()
        data object CountUp : ActionBarTimerMode()
    }

    sealed class ActionBarTimerState {
        data object Running : ActionBarTimerState()
        data object Paused : ActionBarTimerState()
        data object Unset : ActionBarTimerState()
    }
}
