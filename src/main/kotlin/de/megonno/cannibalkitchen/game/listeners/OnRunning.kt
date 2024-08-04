package de.megonno.cannibalkitchen.game.listeners

import com.destroystokyo.paper.event.block.BlockDestroyEvent
import de.megonno.cannibalkitchen.CannibalKitchen
import de.megonno.cannibalkitchen.game.state.GameState
import de.megonno.cannibalkitchen.game.state.GameStateChangeEvent
import de.megonno.cannibalkitchen.register.Displays
import org.bukkit.Material
import org.bukkit.block.data.Ageable
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockGrowEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

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
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (gameManager.gameStateHandler.gameState != GameState.Running) return

        Displays.orderBossBar(event.player)
        Displays.gameScoreboard(event.player)
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerLeave(event: PlayerQuitEvent) {
        if (gameManager.gameStateHandler.gameState != GameState.Running) return

        plugin.bossBarWorkers[event.player.uniqueId]?.destroy()
        plugin.scoreboardWorkers[event.player.uniqueId]?.destroy()
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
