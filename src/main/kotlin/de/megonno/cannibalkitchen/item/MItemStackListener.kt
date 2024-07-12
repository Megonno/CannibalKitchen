package de.megonno.cannibalkitchen.item

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

// ToDo: Clean up this mess

@Suppress("UNUSED")
class MItemStackListener(private val mItemHandlers: MutableMap<String, MItemHandler>) : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInteract(event: PlayerInteractEvent) {
        if (event.item?.itemMeta?.persistentDataContainer?.has(MItemHandler.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemHandlers[event.item!!.handler] ?: return

        event.isCancelled = mItemStackFunctionHandler.interactHandler(event)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        if (!event.itemDrop.itemStack.itemMeta.persistentDataContainer.has(MItemHandler.itemHandlerKey)) return

        val mItemStackFunctionHandler = mItemHandlers[event.itemDrop.itemStack.handler] ?: return

        event.isCancelled = mItemStackFunctionHandler.dropHandler(event)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onSwapHand(event: PlayerSwapHandItemsEvent) {
        if (event.offHandItem.itemMeta?.persistentDataContainer?.has(MItemHandler.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemHandlers[event.offHandItem.handler] ?: return

        event.isCancelled = mItemStackFunctionHandler.swapHandHandler(event)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem?.itemMeta?.persistentDataContainer?.has(MItemHandler.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemHandlers[event.currentItem!!.handler] ?: return

        event.isCancelled = mItemStackFunctionHandler.inventoryClickHandler(event)
    }
}
