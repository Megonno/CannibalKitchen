package de.megonno.cannibalkitchen.item

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.persistence.PersistentDataType

// ToDo: Clean up this mess

class MItemStackListener(private val mItemStackFunctionHandlers: MutableMap<String, MItemStackFunctionHandler>) : Listener {
    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInteract(event: PlayerInteractEvent) {
        if (event.item?.itemMeta?.persistentDataContainer?.has(MItemStack.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemStackFunctionHandlers[
            event.item!!.itemMeta.persistentDataContainer.get(
                MItemStack.itemHandlerKey,
                PersistentDataType.STRING
            )
        ] ?: return

        event.isCancelled = mItemStackFunctionHandler.interactHandler?.invoke(event) ?: false
    }

    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        if (!event.itemDrop.itemStack.itemMeta.persistentDataContainer.has(MItemStack.itemHandlerKey)) return

        val mItemStackFunctionHandler = mItemStackFunctionHandlers[
            event.itemDrop.itemStack.itemMeta.persistentDataContainer.get(
                MItemStack.itemHandlerKey,
                PersistentDataType.STRING
            )
        ] ?: return

        event.isCancelled = mItemStackFunctionHandler.dropHandler?.invoke(event) ?: false
    }

    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onSwapHand(event: PlayerSwapHandItemsEvent) {
        if (event.offHandItem.itemMeta?.persistentDataContainer?.has(MItemStack.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemStackFunctionHandlers[
            event.offHandItem.itemMeta.persistentDataContainer.get(
                MItemStack.itemHandlerKey,
                PersistentDataType.STRING
            )
        ] ?: return

        event.isCancelled = mItemStackFunctionHandler.swapHandHandler?.invoke(event) ?: false
    }

    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.currentItem?.itemMeta?.persistentDataContainer?.has(MItemStack.itemHandlerKey) != true) return

        val mItemStackFunctionHandler = mItemStackFunctionHandlers[
            event.currentItem!!.itemMeta.persistentDataContainer.get(
                MItemStack.itemHandlerKey,
                PersistentDataType.STRING
            )
        ] ?: return

        event.isCancelled = mItemStackFunctionHandler.inventoryClickHandler?.invoke(event) ?: false
    }
}
