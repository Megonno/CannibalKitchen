package de.megonno.cannibalkitchen.inventory.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent

abstract class MGui {
    abstract fun open(player: Player)
    
    abstract fun close(
        player: Player,
        reason: InventoryCloseEvent.Reason = InventoryCloseEvent.Reason.PLUGIN
    )
}
