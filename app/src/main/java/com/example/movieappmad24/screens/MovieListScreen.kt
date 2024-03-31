package com.example.movieappmad24.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.movieappmad24.Screen.Screen
import com.example.movieappmad24.components.BottomNavigationBar
import com.example.movieappmad24.components.MovieTopAppBar
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

@Composable
fun MovieApp() {
    MovieAppMAD24Theme {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.MovieList.route) {
            composable(Screen.MovieList.route) {
                MovieListScreen(navController = navController)
            }
            composable(Screen.DetailScreen.route) { backStackEntry ->
                DetailScreen(navController = navController, movieId = backStackEntry.arguments?.getString("movieId") ?: "")
            }
            composable(Screen.WatchlistScreen.route) {
                WatchlistScreen(navController = navController)
            }
        }
    }
}
@Composable
fun MovieListScreen(navController: NavController) {
    Scaffold(
        topBar = { MovieTopAppBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        val movies = getMovies() // Fetch movies
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(movies.size) { index ->
                MovieRow(movie = movies[index], onClick = {
                    navController.navigate(Screen.DetailScreen.createRoute(movies[index].id))
                })
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            movie.images.firstOrNull()?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
        }
    }
}