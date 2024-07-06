package de.megonno.cannibalkitchen.game.upgrades

import de.megonno.cannibalkitchen.CannibalKitchen
import org.bukkit.entity.Player
import java.util.UUID

class UpgradeHandler(private val plugin: CannibalKitchen) {
    private val _upgrades = mutableMapOf<UUID, MutableSet<Upgrades>>()
    val upgrades: Map<UUID, Set<Upgrades>>
        get() = _upgrades

    fun addUpgrade(player: Player, newUpgrades: Set<Upgrades>) {
        newUpgrades.forEach { upgrade ->
            if (upgrades[player.uniqueId]?.contains(upgrade) != true) {
                upgrade.onActivated(player = player, plugin = plugin)
            }
        }

        _upgrades[player.uniqueId] = _upgrades.getOrPut(player.uniqueId) { mutableSetOf() }.apply { addAll(newUpgrades) }
    }

    fun removeUpgrade(player: Player, upgradesToRemove: Set<Upgrades>) {
        _upgrades[player.uniqueId] =
            _upgrades.getOrPut(player.uniqueId) { mutableSetOf() }.apply { removeAll(upgradesToRemove.toSet()) }
    }
}
