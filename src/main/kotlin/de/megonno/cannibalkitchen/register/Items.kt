package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.item.addEnchantmentEffect
import de.megonno.cannibalkitchen.item.handler
import de.megonno.cannibalkitchen.item.setTag
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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

    val void = {
        ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply {
            handler = "void"
            itemMeta = itemMeta.apply {
                displayName(Component.empty())
                itemName(Component.text("void"))
            }
        }
    }

    val locked = {
        ItemStack(Material.BARRIER).apply {
            handler = "void"
            itemMeta = itemMeta.apply {
                displayName(Component.empty())
                itemName(Component.text("locked"))
            }
        }
    }

    val upgrader = {
        ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE).apply {
            handler = "upgrader"
            itemMeta = itemMeta.apply {
                itemName(Component.text("upgrader"))
                displayName(
                    Component.text("Upgrader").color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false)
                        .decoration(TextDecoration.BOLD, true)
                )
            }
        }
    }

    val speedUpgrade = { bought: Boolean ->
        ItemStack(Material.FEATHER).apply {
            handler = "speed_upgrade"
            itemMeta = itemMeta.apply {
                itemName(Component.text("speed_upgrade"))
                if (bought) {
                    displayName(
                        Component.text("Speed Upgrade (bought)").color(NamedTextColor.RED)
                            .decoration(TextDecoration.ITALIC, false)
                    )
                } else {
                    displayName(
                        Component.text("Speed Upgrade (click to buy)").color(NamedTextColor.GREEN)
                            .decoration(TextDecoration.ITALIC, false)
                    )
                }
            }

            if (bought) {
                addEnchantmentEffect()
                setTag(ItemTags.boughtTag, true)
            } else {
                setTag(ItemTags.boughtTag, false)
            }
        }
    }

    val moreHotbarSlots = { bought: Boolean ->
        ItemStack(Material.CHEST).apply {
            handler = "more_hotbar_slots"
            itemMeta = itemMeta.apply {
                itemName(Component.text("more_hotbar_slots"))

                if (bought) {
                    displayName(
                        Component.text("More Hotbar Slots (bought)").color(NamedTextColor.RED)
                            .decoration(TextDecoration.ITALIC, false)
                    )
                } else {
                    displayName(
                        Component.text("More Hotbar Slots (click to buy)").color(NamedTextColor.GREEN)
                            .decoration(TextDecoration.ITALIC, false)
                    )
                }
            }

            if (bought) {
                addEnchantmentEffect()
                setTag(ItemTags.boughtTag, true)
            } else {
                setTag(ItemTags.boughtTag, false)
            }
        }
    }
}
