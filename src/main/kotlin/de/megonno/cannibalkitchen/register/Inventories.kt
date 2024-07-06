package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.upgrades.Upgrades
import de.megonno.cannibalkitchen.inventory.MInventoryFunctionHandler
import de.megonno.cannibalkitchen.inventory.inventories.MChestInventory
import net.kyori.adventure.text.Component

object Inventories {
    private lateinit var plugin: CannibalKitchen

    val upgradeInventory = {
        MChestInventory(
            plugin = plugin,
            name = Component.text("Upgrades"),
            lines = 3,
            mInventoryFunctionHandler = MInventoryFunctionHandler(
                openHandler = { event ->
                    mutableListOf(Upgrades.Speed to 11, Upgrades.MoreHotbarSlots to 15).forEach { pair ->
                        setItems(pair.second to pair.first.itemConstructor(
                            plugin.gameManager.upgradeHandler.upgrades[event.player.uniqueId]?.contains(pair.first) == true
                        ))
                    }

                    false
                }
            )
        ).apply {
            fill(Items.void())
        }
    }

    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin
    }
}
