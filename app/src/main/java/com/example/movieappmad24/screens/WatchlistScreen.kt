package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.components.BottomNavigationBar
import com.example.movieappmad24.components.MovieTopAppBar
import com.example.movieappmad24.models.Movie

@Composable
fun WatchlistScreen(navController: NavController) {
    Scaffold(
        topBar = { MovieTopAppBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Assuming MovieRow is your composable to display movie details
            // Since it's not shown here, replace this with your actual movie display logic
            MovieRow(movie = Movie(
                id = "tt0499549",
                title = "Avatar",
                year = "2009",
                genre = "Action, Adventure, Fantasy",
                director = "James Cameron",
                actors = "Sam Worthington, Zoe Saldana, Sigourney Weaver, Stephen Lang",
                plot = "A paraplegic marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
                images = listOf(
                    "https://images-na.ssl-images-amazon.com/images/M/MV5BMjEyOTYyMzUxNl5BMl5BanBnXkFtZTcwNTg0MTUzNA@@._V1_SX1500_CR0,0,1500,999_AL_.jpg", // Replace with actual URLs
                ),
                trailer = "trailer_placeholder",
                rating = "7.9"
            ), onClick = {})
        }
    }
}