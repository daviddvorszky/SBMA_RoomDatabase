package com.example.sbma_roomdatabase.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.RESTRICT
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Team::class,
            onDelete = RESTRICT,
            parentColumns = ["id"],
            childColumns = ["teamId"]
        )
    ]
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val firstName: String,
    val lastName: String,
    val teamId: Long
){
    override fun toString() = "$firstName $lastName"
}
