package com.example.sbma_roomdatabase.db

import androidx.room.Embedded
import androidx.room.Relation

class TeamPlayer {
    @Embedded
    var team: Team? = null
    @Relation(parentColumn = "id", entityColumn = "teamId")
    var players: List<Player>? = null
}