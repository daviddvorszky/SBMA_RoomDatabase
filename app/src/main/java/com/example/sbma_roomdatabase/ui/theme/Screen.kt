package com.example.sbma_roomdatabase.ui.theme

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object PlayerListScreen : Screen("player_list_screen")
    object TeamListScreen : Screen("team_list_screen")
    object AddTeamScreen : Screen("add_team_screen")
    object AddPlayerScreen : Screen("add_player_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}