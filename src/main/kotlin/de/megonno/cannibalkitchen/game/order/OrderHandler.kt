package de.megonno.cannibalkitchen.game.order

import java.util.UUID

class OrderHandler {
    val orders = mutableMapOf<UUID, MutableList<Order>>()
}
