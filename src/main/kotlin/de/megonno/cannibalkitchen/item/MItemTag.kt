package de.megonno.cannibalkitchen.item

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class MItemTag<C : Any>(val key: NamespacedKey, val type: PersistentDataType<*, C>, val defaultContent: C)

fun <C : Any> ItemStack.hasTag(tag: MItemTag<C>): Boolean {
    return itemMeta.persistentDataContainer.has(tag.key)
}

fun <C : Any> ItemStack.setTag(tag: MItemTag<C>, content: C) {
    itemMeta = itemMeta.apply { persistentDataContainer.set(tag.key, tag.type, content) }
}

fun <C : Any> ItemStack.getTag(tag: MItemTag<C>): C? {
    return itemMeta.persistentDataContainer.get(tag.key, tag.type)
}

fun <C : Any> ItemStack.getOrCreate(tag: MItemTag<C>): C {
    if (!hasTag(tag)) setTag(tag, tag.defaultContent)

    return getTag(tag)!!
}

fun <C : Any> ItemStack.removeTag(tag: MItemTag<C>) {
    itemMeta = itemMeta.apply { persistentDataContainer.remove(tag.key) }
}
