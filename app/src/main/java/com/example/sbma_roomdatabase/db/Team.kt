package com.example.sbma_roomdatabase.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
){
    override fun toString(): String = "$name ($id)"
}
