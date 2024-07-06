package de.megonno.cannibalkitchen.game.upgrades

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.register.Items
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

sealed class Upgrades(val itemConstructor: (Boolean) -> ItemStack) {
    data object Speed : Upgrades(Items.speedUpgrade) {
        override fun onActivated(player: Player, plugin: CannibalKitchen) {
            player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, -1, 1, false, false, false))
        }
    }

    data object MoreHotbarSlots : Upgrades(Items.moreHotbarSlots) {
        override fun onActivated(player: Player, plugin: CannibalKitchen) {

        }
    }

    abstract fun onActivated(player: Player, plugin: CannibalKitchen)
}
