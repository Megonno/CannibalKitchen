package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.item.MItemTag
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

object ItemTags {
    val enchantmentEffectTag = MItemTag(NamespacedKey("mapi", "enchantment_effect"), PersistentDataType.BOOLEAN, false)

    val boughtTag = MItemTag(NamespacedKey("mapi", "bought"), PersistentDataType.BOOLEAN, false)

    val boughtLevelTag = MItemTag(NamespacedKey("mapi", "bought_level"), PersistentDataType.INTEGER, 0)
}
