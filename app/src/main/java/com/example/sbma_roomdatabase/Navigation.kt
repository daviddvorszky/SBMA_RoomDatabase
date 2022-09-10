package com.example.sbma_roomdatabase

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.sbma_roomdatabase.test.DataProvider
import com.example.sbma_roomdatabase.test.Player
import com.example.sbma_roomdatabase.test.Team
import com.example.sbma_roomdatabase.ui.theme.Screen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.PlayerListScreen.route) {
            PlayerListScreen(navController = navController)
        }
        composable(route = Screen.TeamListScreen.route) {
            TeamListScreen(navController = navController)
        }
        composable(route = Screen.AddTeamScreen.route){
            AddTeamScreen()
        }
        composable(route = Screen.AddPlayerScreen.route){
            AddPlayerScreen()
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
fun PlayerListScreen(navController: NavController) {
    val teams = DataProvider.teams
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Players", fontSize = 46.sp, fontWeight = FontWeight.Bold)
            Button(onClick = {
                navController.navigate(Screen.AddPlayerScreen.route)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            teams.forEach() {
                item {
                    Text(it.name, fontSize = 32.sp)
                }
                ShowPlayersOfTeam(it)
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

fun LazyListScope.ShowPlayersOfTeam(team: Team) {
    if (team.players.size > 0)
        items(team.players) { player ->
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
fun TeamListScreen(navController: NavController) {
    val teams = DataProvider.teams
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
            teams.forEach { team ->
                item {
                    Text(text = team.name, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}


@Composable
fun AddTeamScreen() {
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
                DataProvider.AddTeam(name.text)
                Toast.makeText(context, "Added team: ${name.text}", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddPlayerScreen() {
    var firstName by remember { mutableStateOf(TextFieldValue(""))}
    var lastName by remember { mutableStateOf(TextFieldValue(""))}

    val options = DataProvider.teams
    var expanded by remember { mutableStateOf(false) }
    var selectedTeam by remember { mutableStateOf(options[0])}

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
                DataProvider.AddPlayer(firstName.text, lastName.text, selectedTeam)
                Toast.makeText(context, "Added player: ${firstName.text} ${lastName.text}", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Submit")
        }
    }
}