package de.megonno.cannibalkitchen.register

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {
    val burgerPan = {
        ItemStack(Material.BREAD).apply {
            itemMeta = itemMeta.apply {
                itemName(Component.translatable("items.burger_pan.name"))
            }
        }
    }
}
