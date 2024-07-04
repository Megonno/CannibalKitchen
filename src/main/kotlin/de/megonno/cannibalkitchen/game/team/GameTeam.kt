package de.megonno.cannibalkitchen.game.team

import net.kyori.adventure.text.Component
import org.bukkit.Location

data class GameTeam(val name: Component, val spawningArea: Pair<Location, Location>)
