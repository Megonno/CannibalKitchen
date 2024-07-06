package de.megonno.cannibalkitchen.inventory

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

open class MInventoryFunctionHandler<I : MInventory<I>>(
    var openHandler: (I.(InventoryOpenEvent) -> Boolean)? = null,
    var closeHandler: (I.(InventoryCloseEvent) -> Unit)? = null,
    var clickHandler: (I.(InventoryClickEvent) -> Boolean)? = null
)
