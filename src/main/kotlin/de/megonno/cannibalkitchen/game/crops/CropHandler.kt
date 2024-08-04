package de.megonno.cannibalkitchen.game.crops

import de.megonno.cannibalkitchen.CannibalKitchen
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.scheduler.BukkitTask
import kotlin.random.Random

class CropHandler(
    private val plugin: CannibalKitchen,
    val crops: MutableList<Pair<Location, Material>>,
    private val updateFrequency: Long = (20 * 1).toLong(),
) {
    private var task: BukkitTask? = null

    init {
        if (crops.any { !setOf(Material.WHEAT).contains(it.second) }) {
            throw IllegalArgumentException("The given material is not a crop!")
        }
    }

    fun reset() {
        crops.forEach { crop ->
            place(crop = crop)
            crop.first.add(0.0, -1.0, 0.0).block.type = Material.FARMLAND
        }
    }

    fun start() {
        task = plugin.server.scheduler.runTaskTimer(plugin, Runnable {
            crops.forEach { crop -> grow(crop = crop) }
        }, updateFrequency, updateFrequency)
    }

    fun stop() {
        task?.cancel()
        task = null
    }

    private fun place(crop: Pair<Location, Material>) {
        crop.first.block.blockData = plugin.server.createBlockData(crop.second) {
            (it as Ageable).age = 0
        }
    }

    private fun grow(crop: Pair<Location, Material>) {
        if (crop.first.block.location.add(0.0, -1.0, 0.0).block.type != Material.FARMLAND) {
            return
        }

        if (crop.first.block.type == crop.second) {
            val cropAgeable = crop.first.block.blockData as Ageable

            if (cropAgeable.age < cropAgeable.maximumAge && Random.nextInt(10) == 0) {
                crop.first.block.blockData = (crop.first.block.blockData as Ageable).apply { age += 1 }
            }
        } else {
            place(crop = crop)
        }
    }
}
