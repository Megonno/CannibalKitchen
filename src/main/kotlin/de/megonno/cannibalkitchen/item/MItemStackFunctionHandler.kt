package de.megonno.cannibalkitchen.item

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class MItemStackFunctionHandler(
    var interactHandler: ((PlayerInteractEvent) -> Boolean)? = null,
    var dropHandler: ((PlayerDropItemEvent) -> Boolean)? = null,
    var swapHandHandler: ((PlayerSwapHandItemsEvent) -> Boolean)? = null,
    var inventoryClickHandler: ((InventoryClickEvent) -> Boolean)? = null
)

fun allBlockedMItemStackFunctionHandler(): MItemStackFunctionHandler =
    MItemStackFunctionHandler({ true }, { true }, { true }, { true })
