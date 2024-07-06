package de.megonno.cannibalkitchen.commands

import com.mojang.brigadier.Command
import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import io.papermc.paper.command.brigadier.Commands
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

@Suppress("UnstableApiUsage")
fun Commands.registerStartCommand(plugin: CannibalKitchen) {
    register(
        Commands.literal("start")
            .executes { context ->
                if (plugin.gameManager.gameStateHandler.tryToChangeGameState(GameState.Starting)) {
                    context.source.sender.sendMessage(
                        Component.text("Game is started").color(TextColor.fromHexString("#00ff00"))
                    )
                } else {
                    context.source.sender.sendMessage(
                        Component.text("Game could not be started").color(TextColor.fromHexString("#ff0000"))
                    )
                }
                Command.SINGLE_SUCCESS
            }
            .build(),
        "Start the game",
        listOf()
    )
}
