package de.megonno.cannibalkitchen.inventory

import de.megonno.cannibalkitchen.inventory.gui.MGui
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryHolder

abstract class MInventory<I : MInventory<I>>(
    val name: Component, var mGui: MGui? = null, val tags: MutableMap<String, String> = mutableMapOf()
) : InventoryHolder {
    abstract val mInventoryFunctionHandler: MInventoryFunctionHandler<I>?

    abstract fun open(player: Player)

    abstract fun close(player: Player, reason: InventoryCloseEvent.Reason = InventoryCloseEvent.Reason.PLUGIN)

    abstract fun update()
}
