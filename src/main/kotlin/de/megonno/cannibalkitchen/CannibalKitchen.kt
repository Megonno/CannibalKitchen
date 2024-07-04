package de.megonno.cannibalkitchen

import de.megonno.cannibalkitchen.commands.registerStartCommand
import de.megonno.cannibalkitchen.game.GameManager
import de.megonno.cannibalkitchen.register.Recipes
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class CannibalKitchen : JavaPlugin() {
    companion object {
        const val ID = "cannibal_kitchen"
    }

    val gameManager by lazy { GameManager(plugin = this, world = server.getWorld("world")!!) }

    override fun onEnable() {
        Translation().register()
        Recipes.register(this)
        registerCommands()
        gameManager.registerEventHandlers(plugin = this)
    }

    @Suppress("UnstableApiUsage")
    private fun registerCommands() {
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().apply {
                registerStartCommand(plugin = this@CannibalKitchen)
            }
        }
    }
}
