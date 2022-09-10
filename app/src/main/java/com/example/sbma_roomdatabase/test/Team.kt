package com.example.sbma_roomdatabase.test

data class Team(
    val id: Int,
    val name: String,
    val players: MutableList<Player>
)
