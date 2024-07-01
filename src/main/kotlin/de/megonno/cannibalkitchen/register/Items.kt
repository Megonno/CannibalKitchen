package de.megonno.cannibalkitchen.register

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {
    val burgerPan = {
        ItemStack(Material.BREAD).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Burger Pan"))
                itemName(Component.text("burger_pan"))
            }
        }
    }

    val burgerPetty = {
        ItemStack(Material.COOKED_BEEF).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Burger Petty"))
                itemName(Component.text("burger_petty"))
            }
        }
    }
}
