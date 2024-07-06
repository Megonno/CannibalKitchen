package de.megonno.cannibalkitchen.inventory.gui.guis

import de.megonno.cannibalkitchen.inventory.MInventory
import de.megonno.cannibalkitchen.inventory.gui.MGui
import de.megonno.cannibalkitchen.inventory.gui.MGuiDataContainer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent

class PagedMGui<C : MGuiDataContainer>(val container: C) : MGui() {
    private val inventories = mutableListOf<MInventory<*>>()
    private var current = 0
        set(value) {
            if (value < inventories.size && value >= 0) field = value
        }
    
    fun addInventory(mInventory: MInventory<*>) {
        mInventory.mGui = this
        inventories.add(mInventory)
    }
    
    fun removeInventory(mInventory: MInventory<*>) {
        mInventory.mGui = null
        inventories.remove(mInventory)
    }
    
    fun setInventory(mInventory: MInventory<*>, slot: Int) {
        // Have to check slot
        mInventory.mGui = this
        inventories[slot] = mInventory
    }
    
    fun removeInventory(position: Int) {
        inventories[position].mGui = null
        inventories.removeAt(position)
    }
    
    /**
     *
     * When executed while last page is open, the inventory reload
     * 
     */
    fun next() {
        current++
        
        inventories[current - 1].inventory.viewers.toMutableList().forEach { open(it as Player) }
    }
    
    /**
     *
     * When executed while first page is open, the inventory reload
     * 
     */
    fun prev() {
        current--

        inventories[current + 1].inventory.viewers.toMutableList().forEach { open(it as Player) }
    }
    
    override fun open(player: Player) {
        if (inventories.isNotEmpty()) {
            inventories[current].open(player)
        }
    }
    
    override fun close(player: Player, reason: InventoryCloseEvent.Reason) {
        inventories[current].close(player, reason)
    }
}

@Suppress("UNCHECKED_CAST")
fun <C : MGuiDataContainer> MGui.pagedMGui(): PagedMGui<C>? = try {
    this as PagedMGui<C>
} catch (_: Exception) {
    null
}
