package de.megonno.cannibalkitchen.item

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object MItemStack {
    val itemTagKey = NamespacedKey("mapi", "tags")

    val itemHandlerKey = NamespacedKey("mapi", "handler")
}

fun ItemStack.tags(): MutableMap<String, String>? = try {
    Json.decodeFromString<MutableMap<String, String>?>(nbt(MItemStack.itemTagKey, PersistentDataType.STRING) ?: "{}")
} catch (_: Exception) {
    null
}

fun ItemStack.tags(tags: MutableMap<String, String>, override: Boolean = false) {
    if (override) {
        nbt(MItemStack.itemTagKey, PersistentDataType.STRING, Json.encodeToString(tags))
    } else {
        nbt(MItemStack.itemTagKey, PersistentDataType.STRING, Json.encodeToString(tags()?.apply { putAll(tags) }))
    }
}

fun ItemStack.handler(): String? = try {
    nbt(MItemStack.itemHandlerKey, PersistentDataType.STRING)
} catch (_: Exception) {
    null
}

fun ItemStack.handler(handler: String?) {
    handler?.let { nbt(MItemStack.itemHandlerKey, PersistentDataType.STRING, it) }
}

fun <T, Z> ItemStack.nbt(key: NamespacedKey, type: PersistentDataType<T, Z>): Z? {
    return itemMeta.persistentDataContainer.get(key, type) ?: throw NullPointerException("NBT data not found")
}

fun <T, Z : Any> ItemStack.nbt(key: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) {
    itemMeta = itemMeta.apply { persistentDataContainer.set(key, type, value) }
}

fun ItemStack.addEnchantmentEffect() {
    itemMeta = itemMeta.apply {
        addUnsafeEnchantment(Enchantment.UNBREAKING, 1)
        tags((tags()?: mutableMapOf()).apply { put("enchantment_effect", "true") })
    }
}

fun ItemStack.removeEnchantmentEffect() {
    itemMeta = itemMeta.apply {
        removeEnchant(Enchantment.UNBREAKING)
        tags()?.apply { remove("enchantment_effect") }?.let { tags(it) }
    }
}
