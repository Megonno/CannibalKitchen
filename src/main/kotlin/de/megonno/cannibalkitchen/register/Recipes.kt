package de.megonno.cannibalkitchen.register

import de.megonno.cannibalkitchen.CannibalKitchen
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe

object Recipes {
    val burgerPanRecipe = {
        ShapelessRecipe(NamespacedKey(CannibalKitchen.ID, "burger_pan_recipe"), Items.burgerPan.invoke()).apply {
            addIngredient(3, ItemStack(Material.WHEAT))
            addIngredient(ItemStack(Material.WATER_BUCKET))
        }
    }

    val burgerPettyRecipe = {
        ShapelessRecipe(NamespacedKey(CannibalKitchen.ID, "burger_petty_recipe"), Items.burgerPetty.invoke()).apply {
            addIngredient(ItemStack(Material.COOKED_BEEF))
            addIngredient(ItemStack(Material.FLINT))
        }
    }

    val burgerRecipe = {
        ShapedRecipe(NamespacedKey(CannibalKitchen.ID, "burger_recipe"), Items.burger.invoke()).apply {
            shape("b", "f", "b")
            setIngredient('b', Items.burgerPan.invoke())
            setIngredient('f', Items.burgerPetty.invoke())
        }
    }

    fun register(plugin: CannibalKitchen) {
        listOf(burgerPanRecipe, burgerPettyRecipe, burgerRecipe).forEach { recipe ->
            plugin.server.addRecipe(recipe.invoke())
        }
        plugin.server.updateRecipes()
    }
}
