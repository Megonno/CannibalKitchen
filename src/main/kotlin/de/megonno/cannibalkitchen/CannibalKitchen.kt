package de.megonno.cannibalkitchen

import de.megonno.cannibalkitchen.commands.registerStartCommand
import de.megonno.cannibalkitchen.displays.BossBarWorker
import de.megonno.cannibalkitchen.displays.ScoreboardWorker
import de.megonno.cannibalkitchen.game.GameManager
import de.megonno.cannibalkitchen.inventory.MInventoryListener
import de.megonno.cannibalkitchen.item.MItemHandler
import de.megonno.cannibalkitchen.item.MItemStackListener
import de.megonno.cannibalkitchen.register.Displays
import de.megonno.cannibalkitchen.register.Inventories
import de.megonno.cannibalkitchen.register.ItemHandlers
import de.megonno.cannibalkitchen.register.Recipes
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class CannibalKitchen : JavaPlugin() {
    companion object {
        const val ID = "cannibal_kitchen"
    }

    val mItemHandlers = mutableMapOf<String, MItemHandler>()
    val bossBarWorkers = mutableMapOf<UUID, BossBarWorker>()
    val scoreboardWorkers = mutableMapOf<UUID, ScoreboardWorker>()

    val gameManager by lazy { GameManager(plugin = this, world = server.getWorld("world")!!) }

    override fun onEnable() {
        registerMApiListener()

        Inventories.register(plugin = this)
        ItemHandlers.register(plugin = this)
        Recipes.register(plugin = this)
        Displays.register(plugin = this)

        Translation().register()
        registerCommands()
        gameManager.registerEventHandlers()
    }

    private fun registerMApiListener() {
        listOf(MItemStackListener(mItemHandlers), MInventoryListener()).forEach { listener ->
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
