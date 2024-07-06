package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.upgrades.Upgrades
import de.megonno.cannibalkitchen.item.MItemStackFunctionHandler
import de.megonno.cannibalkitchen.item.allBlockedMItemStackFunctionHandler
import de.megonno.cannibalkitchen.item.tags
import org.bukkit.entity.Player

object ItemFunctions {
    private lateinit var plugin: CannibalKitchen

    private val void = allBlockedMItemStackFunctionHandler()

    private val upgrader = MItemStackFunctionHandler(
        interactHandler = { event ->
            Inventories.upgradeInventory().open(event.player)

            true
        },
        dropHandler = { true }
    )

    private val speedUpgrade = allBlockedMItemStackFunctionHandler().apply {
        inventoryClickHandler = { event ->
            val item = event.currentItem!!

            if (item.tags()?.contains("bought") != true || item.tags()?.get("bought") == "false") {
                event.currentItem = Items.speedUpgrade(true)
                plugin.gameManager.upgradeHandler.addUpgrade(event.whoClicked as Player, setOf(Upgrades.Speed))
            }

            true
        }
    }

    private val moreHotbarSlots = allBlockedMItemStackFunctionHandler().apply {
        inventoryClickHandler = { event ->
            val item = event.currentItem!!

            if (item.tags()?.contains("bought") != true || item.tags()?.get("bought") == "false") {
                event.currentItem = Items.moreHotbarSlots(true)
                plugin.gameManager.upgradeHandler.addUpgrade(event.whoClicked as Player, setOf(Upgrades.MoreHotbarSlots))
            }

            true
        }
    }


    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin
        plugin.mItemStackFunctionHandlers.putAll(
            mutableMapOf(
                "void" to void,
                "upgrader" to upgrader,
                "speed_upgrade" to speedUpgrade,
                "more_hotbar_slots" to moreHotbarSlots
            )
        )
    }
}
