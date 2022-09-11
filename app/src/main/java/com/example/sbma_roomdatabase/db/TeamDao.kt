package com.example.sbma_roomdatabase.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TeamDao {
    @Query("SELECT * FROM team")
    fun getAll(): LiveData<List<Team>>

    @Query("SELECT * FROM team")
    fun getTeamsWithPlayers(): LiveData<List<TeamPlayer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team): Long

     @Update
     suspend fun update(team: Team)

     @Delete
     suspend fun delete(team: Team)
}