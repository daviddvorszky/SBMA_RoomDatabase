package com.example.sbma_roomdatabase.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TeamViewModel(application: Application): AndroidViewModel(application) {
    private val appDB = AppDatabase.get(application)

    fun getAll(): LiveData<List<Team>> = appDB.teamDao().getAll()

    fun getTeamsWithPlayers() = appDB.teamDao().getTeamsWithPlayers()

    fun insert(team: Team) {
        viewModelScope.launch {
            appDB.teamDao().insert(team)
        }
    }

    fun update(team: Team){
        viewModelScope.launch {
            appDB.teamDao().update(team)
        }
    }

    fun delete(team: Team){
        viewModelScope.launch {
            appDB.teamDao().delete(team)
        }
    }
}