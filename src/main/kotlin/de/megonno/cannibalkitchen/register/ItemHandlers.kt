package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.upgrades.Upgrades
import de.megonno.cannibalkitchen.item.MItemHandler
import de.megonno.cannibalkitchen.item.getTag
import de.megonno.cannibalkitchen.item.hasTag
import org.bukkit.entity.Player

object ItemHandlers {
    private lateinit var plugin: CannibalKitchen

    private val void = MItemHandler(
        registeredName = "void",
        interactHandler = { true },
        dropHandler = { true },
        swapHandHandler= { true },
        inventoryClickHandler = { true }
    )

    private val upgrader = MItemHandler(
        registeredName = "upgrader",
        interactHandler = { event ->
            Inventories.upgradeInventory().open(event.player)

            true
        },
        dropHandler = { true }
    )

    private val speedUpgrade = MItemHandler(
        registeredName = "speed_upgrade",
        inventoryClickHandler = { event ->
            val item = event.currentItem!!

            if (!item.hasTag(ItemTags.boughtTag) || item.getTag(ItemTags.boughtTag) == false) {
                event.currentItem = Items.speedUpgrade(true)
                plugin.gameManager.upgradeHandler.addUpgrade(event.whoClicked as Player, setOf(Upgrades.Speed))
            }

            println(item.itemMeta.persistentDataContainer.keys.joinToString { it.key })

            true
        }
    )

    private val moreHotbarSlots = MItemHandler(
        registeredName = "more_hotbar_slots",
        inventoryClickHandler = { event ->
            val item = event.currentItem!!

            if (!item.hasTag(ItemTags.boughtTag) || item.getTag(ItemTags.boughtTag) == false) {
                event.currentItem = Items.moreHotbarSlots(true)
                plugin.gameManager.upgradeHandler.addUpgrade(event.whoClicked as Player, setOf(Upgrades.MoreHotbarSlots))
            }

            true
        }
    )

    fun register(plugin: CannibalKitchen) {
        this.plugin = plugin

        plugin.mItemHandlers.putAll(
            listOf(void, upgrader, speedUpgrade, moreHotbarSlots).associateBy { it.registeredName }
        )
    }
}
