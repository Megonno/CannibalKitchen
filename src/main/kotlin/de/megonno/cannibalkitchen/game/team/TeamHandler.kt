package de.megonno.cannibalkitchen.game.team

import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.UUID

class TeamHandler(teamList: List<GameTeam>, var playersPerTeam: Int = 4) : Listener {
    private val players = mutableMapOf<UUID, UUID>()
    private val teams = mutableMapOf<UUID, GameTeam>().apply { putAll(teamList.associateBy { UUID.randomUUID() }) }
    val maxPlayers by lazy { playersPerTeam * teams.size }

    fun addPlayerToRandomTeam(player: Player) {
        if (players.entries.size < maxPlayers) {
            players[player.uniqueId] = teams.entries.random().key
        }
    }

    fun playerChangeTeam(player: Player, teamUUID: UUID): Boolean {
        return if (players.contains(player.uniqueId) && teams.contains(teamUUID)) {
            if (players[player.uniqueId] != teamUUID && players.filter { it.value == teamUUID }.size < playersPerTeam) {
                players[player.uniqueId] = teamUUID
                true
            } else false
        } else false
    }

    fun getTeamIdByPlayer(player: Player): UUID? = players[player.uniqueId]

    fun getTeamById(uuid: UUID): GameTeam? = teams[uuid]

    fun getTeamByPlayer(player: Player): GameTeam? = getTeamIdByPlayer(player)?.let { getTeamById(it) }
}
