package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.upgrades.Upgrades
import de.megonno.cannibalkitchen.inventory.MInventoryFunctionHandler
import de.megonno.cannibalkitchen.inventory.inventories.MChestInventory
import de.megonno.cannibalkitchen.item.handler
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

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
            fill(ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply { handler("void") })
        }
    }

    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin
    }
}
