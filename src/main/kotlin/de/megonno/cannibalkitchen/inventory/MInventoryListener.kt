package de.megonno.cannibalkitchen.inventory

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

// ToDo: Clean up this mess

@Suppress("UNCHECKED_CAST")
class MInventoryListener : Listener {
    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.inventory.holder !is MInventory<*>) return
        
        val mInventory = event.inventory.holder as MInventory<*>
        val mInventoryFunctionHandler =
            mInventory.mInventoryFunctionHandler?.openHandler as (MInventory<*>.(InventoryOpenEvent) -> Boolean)?
        
        event.isCancelled = mInventoryFunctionHandler?.invoke(mInventory, event) ?: false
    }

    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder !is MInventory<*>) return
        
        val mInventory = event.inventory.holder as MInventory<*>
        val mInventoryFunctionHandler =
            mInventory.mInventoryFunctionHandler?.closeHandler as (MInventory<*>.(InventoryCloseEvent) -> Boolean)?
        
        mInventoryFunctionHandler?.invoke(mInventory, event)
    }

    @Suppress("UNUSED")
    @EventHandler(priority = EventPriority.HIGH)
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder !is MInventory<*>) return
        
        val mInventory = event.inventory.holder as MInventory<*>
        val mInventoryFunctionHandler =
            mInventory.mInventoryFunctionHandler?.clickHandler as (MInventory<*>.(InventoryClickEvent) -> Boolean)?
        
        event.isCancelled = mInventoryFunctionHandler?.invoke(mInventory, event) ?: false
    }
}
