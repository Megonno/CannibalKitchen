package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe

object Recipes {
    val burgerPanRecipe = {
        ShapelessRecipe(NamespacedKey(CannibalKitchen.ID, "burger_pan_recipe"), Items.burgerPan.invoke()).apply {
            addIngredient(3, ItemStack(Material.WHEAT))
            addIngredient(ItemStack(Material.WATER_BUCKET))
        }
    }

    fun register(plugin: CannibalKitchen) {
        listOf(burgerPanRecipe).forEach { recipe ->
            plugin.server.addRecipe(recipe.invoke())
            plugin.server.updateRecipes()
        }
    }
}
