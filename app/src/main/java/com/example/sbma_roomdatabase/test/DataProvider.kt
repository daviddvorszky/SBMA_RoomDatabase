package com.example.sbma_roomdatabase.test

object DataProvider {
    val teams: MutableList<Team> = java.util.ArrayList()
    val players: MutableList<Player> = java.util.ArrayList()

    init {
        AddTeam("Yellow Ostriches")
        AddTeam("Hungry Raiders")
        AddTeam("Wales Athletic")
        AddTeam("Malicious Tigers")


        players.add(createPlayer("Anandi", "Nefertiti"))
        players.add(createPlayer("Wilma", "Aurora"))
        players.add(createPlayer("Pwyll", "Lauge"))
        players.add(createPlayer("Sigmund", "Connie"))
        players.add(createPlayer("Brage", "Rachna"))
        players.add(createPlayer("Chouko", "Patrik"))
        players.add(createPlayer("Rashad", "Saturninus"))
        players.add(createPlayer("Muhtar", "Archie"))
        players.add(createPlayer("Raheem", "Isapo-Muxika"))
        players.add(createPlayer("Wanda", "Fevronia"))

        players.forEach { player ->
            assignPlayerToTeam(player, teams[player.lastName.length % teams.size])
        }
    }

    var tid = 0;
    var pid = 0;

    private fun createTeam(name: String): Team {
        val players: MutableList<Player> = java.util.ArrayList()
        val team = Team(tid, name, players)
        tid += 1
        return team
    }

    private fun createPlayer(firstName: String, lastName: String): Player{
        val player = Player(pid, firstName, lastName, null)
        pid += 1
        return player
    }

    public fun AddTeam(name: String){
        teams.add(createTeam(name))
    }

    public fun AddPlayer(firstName: String, lastName: String, team: Team){
        val player = createPlayer(firstName, lastName)
        assignPlayerToTeam(player, team)
        players.add(player)
    }

    public fun RemovePlayer(player: Player){
        val team = player.team
        team?.players?.remove(player)
        players.remove(player)
    }

    private fun assignPlayerToTeam(player: Player, team: Team){
        team.players.add(player)
        player.team = team
    }
}