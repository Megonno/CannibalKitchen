package de.megonno.cannibalkitchen

import de.megonno.cannibalkitchen.commands.registerStartCommand
import de.megonno.cannibalkitchen.game.GameManager
import de.megonno.cannibalkitchen.inventory.MInventoryListener
import de.megonno.cannibalkitchen.item.MItemStackFunctionHandler
import de.megonno.cannibalkitchen.item.MItemStackListener
import de.megonno.cannibalkitchen.register.Inventories
import de.megonno.cannibalkitchen.register.ItemFunctions
import de.megonno.cannibalkitchen.register.Recipes
import de.megonno.cannibalkitchen.register.Scoreboards
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class CannibalKitchen : JavaPlugin() {
    companion object {
        const val ID = "cannibal_kitchen"
    }

    val mItemStackFunctionHandlers = mutableMapOf<String, MItemStackFunctionHandler>()
    val gameManager by lazy { GameManager(plugin = this, world = server.getWorld("world")!!) }

    override fun onEnable() {
        registerMApiListener()
        Inventories.register(plugin = this)
        ItemFunctions.register(plugin = this)
        Scoreboards.register(plugin = this)
        Translation().register()
        Recipes.register(this)
        registerCommands()
        gameManager.registerEventHandlers()
    }

    private fun registerMApiListener() {
        listOf(MItemStackListener(mItemStackFunctionHandlers), MInventoryListener()).forEach { listener ->
            server.pluginManager.registerEvents(listener, this)
        }
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
