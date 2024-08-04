package de.megonno.cannibalkitchen.game.order

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.register.Items
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.UUID
import kotlin.random.Random

class OrderHandler(private val plugin: CannibalKitchen) {
    private val currentOrders = mutableMapOf<UUID, Order>()
    private val finishedOrders = mutableMapOf<UUID, List<Order>>()

    fun getCurrentOrder(teamId: UUID): Order = currentOrders.getOrPut(teamId) { generateNewOrder() }

    fun nextOder(teamId: UUID): Order {
        val newOrder = generateNewOrder()
        currentOrders[teamId]?.let {
            finishedOrders[teamId] = finishedOrders.getOrDefault(teamId, listOf()).apply { toMutableList().add(it) }
        }
        currentOrders[teamId] = newOrder
        return newOrder
    }

    fun winner(): Set<UUID> {
        val max = finishedOrders.map { it.value.size to it.key }.maxByOrNull { it.first }
            ?: return plugin.gameManager.teamHandler.getTeams().keys
        return finishedOrders.map { it.value.size to it.key }.filter { it == max }.map { it.second }.toSet()
    }


    fun change(uuid: UUID, change: () -> Unit) {
        currentOrders.getOrDefault(uuid, null)?.items

        // ToDO Imple
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
