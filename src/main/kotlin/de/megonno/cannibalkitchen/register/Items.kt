package de.megonno.cannibalkitchen.register

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Items {
    val burgerPan = {
        ItemStack(Material.BREAD).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Burger Pan").decoration(TextDecoration.ITALIC, false))
                itemName(Component.text("burger_pan"))
            }
        }
    }

    val burgerPetty = {
        ItemStack(Material.COOKED_BEEF).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Burger Petty").decoration(TextDecoration.ITALIC, false))
                itemName(Component.text("burger_petty"))
                setCustomModelData(1)
            }
        }
    }

    val burger = {
        ItemStack(Material.BREAD).apply {
            itemMeta = itemMeta.apply {
                displayName(Component.text("Burger").decoration(TextDecoration.ITALIC, false))
                itemName(Component.text("burger"))
                setCustomModelData(1)
            }
        }
    }
}
