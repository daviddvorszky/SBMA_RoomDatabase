package com.example.sbma_roomdatabase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE player.teamId = :teamId")
    fun getPlayersOfTeam(teamId: Int): LiveData<List<Player>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player): Long

    @Update
    suspend fun update(player: Player)

    @Delete
    suspend fun delete(player: Player)
}