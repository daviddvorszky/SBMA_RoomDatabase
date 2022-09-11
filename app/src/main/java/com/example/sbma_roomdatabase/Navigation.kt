package com.example.sbma_roomdatabase

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sbma_roomdatabase.db.*
import com.example.sbma_roomdatabase.ui.theme.Screen

@Composable
fun Navigation(teamViewModel: TeamViewModel, playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.PlayerListScreen.route) {
            PlayerListScreen(navController = navController, teamViewModel, playerViewModel)
        }
        composable(route = Screen.TeamListScreen.route) {
            TeamListScreen(navController = navController, teamViewModel)
        }
        composable(route = Screen.AddTeamScreen.route){
            AddTeamScreen(teamViewModel)
        }
        composable(route = Screen.AddPlayerScreen.route){
            AddPlayerScreen(teamViewModel, playerViewModel)
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { navController.navigate(Screen.TeamListScreen.route) },
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "",
                    modifier = Modifier.size(90.dp)
                )
                Text("Teams", fontSize = 24.sp)
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable { navController.navigate(Screen.PlayerListScreen.route) },
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier.padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "",
                    modifier = Modifier.size(90.dp)
                )
                Text("Players", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun PlayerListScreen(navController: NavController, teamViewModel: TeamViewModel, playerViewModel: PlayerViewModel) {
    val teamsState = teamViewModel.getAll().observeAsState(listOf())
    val playersState = playerViewModel.getAll().observeAsState(listOf())
    val context = LocalContext.current
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Players", fontSize = 46.sp, fontWeight = FontWeight.Bold)
            Button(onClick = {
                if(teamsState.value.isEmpty()){
                    Toast.makeText(context, "Create a team first!", Toast.LENGTH_SHORT).show()
                }else {
                    navController.navigate(Screen.AddPlayerScreen.route)
                }
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            teamsState.value.forEach() { team ->
                item {
                    Text(team.name, fontSize = 32.sp)
                }
                ShowPlayersOfTeam(playersState.value.filter { player -> player.teamId == team.id })
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

fun LazyListScope.ShowPlayersOfTeam(players: List<Player>) {
    if (players.isNotEmpty())
        items(players) { player ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("${player.firstName} ${player.lastName}", fontSize = 20.sp)
                /*Row() {
                    Button(onClick = { }, enabled = false) {
                        Text("Edit")
                    }
                    Button(onClick = {
                        DataProvider.RemovePlayer(player)
                    }) {
                        Text("Delete")
                    }
                }*/

            }
        }
    else
        item {
            Text("Team is empty")
        }
}

@Composable
fun TeamListScreen(navController: NavController, teamViewModel: TeamViewModel) {
    val teams = teamViewModel.getTeamsWithPlayers().observeAsState(listOf())
    val context = LocalContext.current
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Teams", fontSize = 46.sp, fontWeight = FontWeight.Bold)
            Button(onClick = {
                navController.navigate(Screen.AddTeamScreen.route)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }
        LazyColumn {
            items(teams.value){ teamplayer ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment =  Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = teamplayer.team!!.name, fontSize = 24.sp)
                    Button(onClick = {
                        if(teamplayer.players!!.isEmpty()) {
                            teamViewModel.delete(teamplayer.team!!)
                        }else{
                            Toast.makeText(context, "You can only delete empty teams", Toast.LENGTH_SHORT).show()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "")
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}


@Composable
fun AddTeamScreen(teamViewModel: TeamViewModel) {
    var name by remember { mutableStateOf(TextFieldValue(""))}
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Team",
            fontSize = 46.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = {
                Text("Team Name")
            }
        )
        Button(onClick = {
            if(name.text.isNotEmpty()){
                teamViewModel.insert(Team(0, name.text))
                Toast.makeText(context, "Added team: ${name.text}", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddPlayerScreen(teamViewModel: TeamViewModel, playerViewModel: PlayerViewModel) {
    var firstName by remember { mutableStateOf(TextFieldValue(""))}
    var lastName by remember { mutableStateOf(TextFieldValue(""))}

    val options = teamViewModel.getAll().observeAsState(listOf()).value
    var expanded by remember { mutableStateOf(false) }
    var selectedTeam by remember { mutableStateOf(if(options.isEmpty()) Team(0, "") else options[0])}

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Add Player",
            fontSize = 46.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        TextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            placeholder = {
                Text("First Name")
            }
        )
        TextField(
            value = lastName,
            onValueChange = {
                lastName = it
            },
            placeholder = {
                Text("Last Name")
            }
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }) {
            TextField(
                readOnly = true,
                value = selectedTeam.name,
                onValueChange = {},
                label = {Text("Team")},
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectedOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedTeam = selectedOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectedOption.name)
                    }
                }
            }
        }
        Button(onClick = {
            if(firstName.text.isNotEmpty() && lastName.text.isNotEmpty()){
                playerViewModel.insert(Player(0, firstName.text, lastName.text, selectedTeam.id))
                Toast.makeText(context, "Added player: ${firstName.text} ${lastName.text}", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
    }
}