package de.megonno.cannibalkitchen.item

import org.bukkit.NamespacedKey
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class MItemHandler(
    val registeredName: String,
    var interactHandler: (PlayerInteractEvent) -> Boolean = { false },
    var dropHandler: (PlayerDropItemEvent) -> Boolean = { false },
    var swapHandHandler: (PlayerSwapHandItemsEvent) -> Boolean = { false },
    var inventoryClickHandler: (InventoryClickEvent) -> Boolean = { false },
) {
    companion object {
        val itemHandlerKey = NamespacedKey("mapi", "handler")
    }
}

var ItemStack.handler: String?
    get() = itemMeta.persistentDataContainer.get(MItemHandler.itemHandlerKey, PersistentDataType.STRING)
    set(value) {
        if (value != null) itemMeta = itemMeta.apply {
            persistentDataContainer.set(MItemHandler.itemHandlerKey, PersistentDataType.STRING, value)
        }
    }
