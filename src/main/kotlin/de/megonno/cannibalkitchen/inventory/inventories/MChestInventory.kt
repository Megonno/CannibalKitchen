package de.megonno.cannibalkitchen.inventory.inventories

import de.megonno.cannibalkitchen.CannibalKitchen
import net.kyori.adventure.text.Component
import de.megonno.cannibalkitchen.inventory.MInventory
import de.megonno.cannibalkitchen.inventory.MInventoryFunctionHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

open class MChestInventory(
    plugin: CannibalKitchen,
    name: Component,
    val lines: Int,
    final override val mInventoryFunctionHandler: MInventoryFunctionHandler<MChestInventory>? = null
) : MInventory<MChestInventory>(name) {
    @get:JvmName("getInternalInventory")
    protected var inventory: Inventory
        private set
    
    init {
        inventory = plugin.server.createInventory(this, lines * 9, name)
    }
    
    internal constructor(
        plugin: CannibalKitchen,
        name: Component,
        defaultInventory: Inventory,
        mInventoryFunctionHandler: MInventoryFunctionHandler<MChestInventory>? = null
    ) : this(plugin, name, defaultInventory.size / 9, mInventoryFunctionHandler) {
        inventory = defaultInventory
    }
    
    open fun setItems(vararg content: Pair<Int, ItemStack>) {
        content.forEach { inventory.setItem(it.first, it.second) }
    }
    
    open fun addItems(vararg items: ItemStack) {
        inventory.addItem(*items)
    }
    
    open fun removeItems(vararg slots: Int) {
        slots.forEach { inventory.clear(it) }
    }
    
    open fun clear() {
        inventory.clear()
    }
    
    open fun getItemStack(slot: Int): ItemStack? = inventory.getItem(slot)
    
    open fun getMItemStack(slot: Int): ItemStack? = inventory.getItem(slot)?.let { ItemStack(it) }
    
    open fun getMItemStacks(): List<ItemStack> {
        val items = mutableListOf<ItemStack>()
        for (i in 0 until inventory.size) getMItemStack(i)?.let { items.add(it) }
        return items
    }
    
    open fun getItemStacks(): List<ItemStack> = inventory.contents.filterNotNull().toMutableList()
    
    open fun fill(item: ItemStack) {
        for (i in 0 until inventory.size) setItems(i to item)
    }
    
    override fun open(player: Player) {
        player.openInventory(inventory)
    }
    
    override fun close(player: Player, reason: InventoryCloseEvent.Reason) {
        player.closeInventory(reason)
    }
    
    override fun update() {
        inventory.viewers.forEach { player -> (player as Player).updateInventory() }
    }
    
    final override fun getInventory() = inventory
}

fun Inventory.toMChestInventory(
    plugin: CannibalKitchen,
    name: Component,
    mInventoryFunctionHandler: MInventoryFunctionHandler<MChestInventory>? = null
): MChestInventory {
    if (listOf(InventoryType.CHEST, InventoryType.PLAYER).contains(this.type)){
        return MChestInventory(plugin, name, this, mInventoryFunctionHandler)
    } else throw Exception("This inventory cannot be converted to a chest inventory!")
}
