package com.example.sbma_roomdatabase.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application): AndroidViewModel(application) {
    private val appDB = AppDatabase.get(application)

    fun getAll(): LiveData<List<Player>> = appDB.playerDao().getAll()

    fun getPlayersOfTeam(teamId: Int): LiveData<List<Player>> = appDB.playerDao().getPlayersOfTeam(teamId)

    fun insert(player: Player){
        viewModelScope.launch{
            appDB.playerDao().insert(player)
        }
    }

    fun update(player: Player){
        viewModelScope.launch{
            appDB.playerDao().update(player)
        }
    }

    fun delete(player: Player){
        viewModelScope.launch{
            appDB.playerDao().delete(player)
        }
    }
}