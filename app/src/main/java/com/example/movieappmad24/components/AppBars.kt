package com.example.movieappmad24.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.Screen.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text("MovieAppMAD24") }
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(Screen.MovieList, Screen.WatchlistScreen) // Add your Screen objects here
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { screen ->
            IconButton(onClick = { navController.navigate(screen.route) }) {
                Icon(
                    imageVector = if (screen == Screen.WatchlistScreen) Icons.Filled.Star else Icons.Filled.Home,
                    contentDescription = screen.route
                )
            }
        }
    }
}