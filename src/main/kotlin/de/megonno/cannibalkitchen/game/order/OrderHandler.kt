package de.megonno.cannibalkitchen.game.order

import de.megonno.cannibalkitchen.register.Items
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.random.Random

class OrderHandler {
    private val currentOrders = mutableMapOf<UUID, Order>()

    fun getCurrentOrder(teamId: UUID): Order = currentOrders.getOrPut(teamId) { generateNewOrder() }

    fun change(uuid: UUID, chang: () -> Unit) {
        currentOrders.getOrDefault(uuid, null)?.items
    }

    private fun generateNewOrder(): Order {
        val burgerAmount = Random.nextInt(1, 4)
        val beefAmount = Random.nextInt(0, 2)

        return Order(
            items = mutableListOf(
                Items.burger().apply { amount = burgerAmount },
                ItemStack(Material.BEEF).apply { amount = beefAmount }
            ),
            coinRewardAmount = burgerAmount * 5 + beefAmount * 3
        )
    }
}
