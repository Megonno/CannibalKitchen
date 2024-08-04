package de.megonno.cannibalkitchen.game.listeners

import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

class OnRunning(private val plugin: CannibalKitchen) : Listener {
    private val gameManager = plugin.gameManager
    
    @EventHandler(priority = EventPriority.HIGH)
    fun onChangeGameState(event: GameStateChangeEvent) {
        if (event.newGameState != GameState.Running) return

        plugin.server.onlinePlayers.forEach { player -> player.sendMessage("started running game state") }
        gameManager.timer.startTimer()
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerChangeItemInHand(event: PlayerItemHeldEvent) {
        if (plugin.gameManager.gameStateHandler.gameState != GameState.Running) return

        if (!(3..5).contains(event.newSlot)) {
            event.isCancelled = true
        }
    }
    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerBreakBlock(event: BlockBreakEvent) {
        if (!gameManager.gameStateHandler.isGameState(GameState.Starting, GameState.Running, GameState.Stopping)) {
            return
        }

        if (event.block.type == Material.WHEAT && gameManager.gameStateHandler.gameState != GameState.Stopping) {
            event.isDropItems = false

            if ((event.block.blockData as Ageable).age >= (event.block.blockData as Ageable).maximumAge) {
                event.block.world.dropItemNaturally(event.block.location, ItemStack(Material.WHEAT))
                event.block.type = Material.WHEAT
            }

            return
        }

        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockDestroyed(event: BlockDestroyEvent) {
        if (!gameManager.gameStateHandler.isGameState(GameState.Starting, GameState.Running, GameState.Stopping)) {
            return
        }

        if (event.block.type == Material.WHEAT && gameManager.gameStateHandler.gameState != GameState.Stopping) {
            event.setWillDrop(false)

            if ((event.block.blockData as Ageable).age >= (event.block.blockData as Ageable).maximumAge) {
                event.block.world.dropItemNaturally(event.block.location, ItemStack(Material.WHEAT))
            }

            return
        }

        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onBlockGrow(event: BlockGrowEvent) {
        if (!gameManager.gameStateHandler.isGameState(GameState.Starting, GameState.Stopping)) {
            return
        }

        event.isCancelled = true
    }
}
