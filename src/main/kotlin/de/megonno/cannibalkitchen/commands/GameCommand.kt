package de.megonno.cannibalkitchen.commands

import com.mojang.brigadier.Command
import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

@Suppress("UnstableApiUsage")
fun Commands.registerStartCommand(plugin: CannibalKitchen) {
    register(
        Commands.literal("game")
            .then(
                Commands.literal("start")
                    .executes { context ->
                        if (plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Starting)) {
                            context.source.sender.sendMessage(
                                Component.text("Game is started").color(NamedTextColor.GREEN)
                            )
                        } else {
                            context.source.sender.sendMessage(
                                Component.text("Game could not be started").color(NamedTextColor.RED)
                            )
                        }
                        Command.SINGLE_SUCCESS
                    }
            )
            .then(
                Commands.literal("stop")
                    .executes { context ->
                        if (plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Stopping)) {
                            context.source.sender.sendMessage(
                                Component.text("Game is stopped").color(NamedTextColor.GREEN)
                            )
                        } else {
                            context.source.sender.sendMessage(
                                Component.text("Game could not be stopped").color(NamedTextColor.RED)
                            )
                        }
                        Command.SINGLE_SUCCESS
                    }
            )
            .then(
                Commands.literal("reset")
                    .executes { context ->
                        if (plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Resetting)) {
                            context.source.sender.sendMessage(
                                Component.text("Game is resetting").color(NamedTextColor.GREEN)
                            )
                        } else {
                            context.source.sender.sendMessage(
                                Component.text("Game could not be resetted").color(NamedTextColor.RED)
                            )
                        }
                        Command.SINGLE_SUCCESS
                    }
            )
            .build(),
        "Start the game",
        listOf()
    )
}
