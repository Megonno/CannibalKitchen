package de.megonno.cannibalkitchen.game.order

import org.bukkit.inventory.ItemStack
import java.util.UUID

data class Order(val itemStack: ItemStack, val coinRewardAmount: Int, val uuid: UUID = UUID.randomUUID())