package de.megonno.cannibalkitchen

import de.megonno.cannibalkitchen.register.Recipes
import org.bukkit.plugin.java.JavaPlugin

class CannibalKitchen : JavaPlugin() {
    companion object {
        const val ID = "cannibalkitchen"
    }

    override fun onEnable() {
        Translation().register()
        Recipes.register(this)
    }
}
