package de.megonno.cannibalkitchen.item

import de.megonno.cannibalkitchen.register.ItemTags
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

fun ItemStack.addEnchantmentEffect() {
    itemMeta = itemMeta.apply {
        addUnsafeEnchantment(Enchantment.UNBREAKING, 1)
        setTag(ItemTags.enchantmentEffectTag, true)
    }
}

fun ItemStack.removeEnchantmentEffect() {
    itemMeta = itemMeta.apply {
        removeEnchant(Enchantment.UNBREAKING)
        removeTag(ItemTags.enchantmentEffectTag)
    }
}
