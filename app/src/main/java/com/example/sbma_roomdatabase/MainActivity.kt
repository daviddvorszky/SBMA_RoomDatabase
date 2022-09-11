package com.example.sbma_roomdatabase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sbma_roomdatabase.db.PlayerViewModel
import com.example.sbma_roomdatabase.db.TeamViewModel
import com.example.sbma_roomdatabase.ui.theme.SBMA_RoomDatabaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SBMA_RoomDatabaseTheme {
                Navigation(TeamViewModel(this.application), PlayerViewModel(this.application))
            }
        }
    }
}
