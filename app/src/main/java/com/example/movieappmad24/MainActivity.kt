package com.example.movieappmad24

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.models.getMovies
import coil.compose.AsyncImage
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }

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
            Column(modifier = Modifier.padding(innerPadding)) {
                movies.forEach { movie ->
                    MovieRow(movie = movie, onClick = {
                        navController.navigate(Screen.DetailScreen.createRoute(movie.id))
                    })
                }
            }
        }
    }

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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun DetailScreen(navController: NavController, movieId: String) {
        val movie = getMovies().firstOrNull { it.id == movieId } ?: return

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = movie.title) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            Column {
                ExpandableMovieDetailCard(movie = movie)
                MovieImagesRow(movie = movie)
            }

        }
    }

    @Composable
    fun ExpandableMovieDetailCard(movie: Movie) {
        var expanded by remember { mutableStateOf(false) }
        val paddingValue = animateDpAsState(targetValue = if (expanded) 16.dp else 8.dp).value

        Card(
            modifier = Modifier
                .padding(8.dp)
                .clickable { expanded = !expanded },
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(paddingValue)
            ) {
                AsyncImage(
                    model = movie.images.firstOrNull(),
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text("Director: ${movie.director}", style = MaterialTheme.typography.bodyLarge)
                        Text("Release Year: ${movie.year}", style = MaterialTheme.typography.bodyLarge)
                        Text("Plot: ${movie.plot}", style = MaterialTheme.typography.bodyLarge)
                        Text("Actors: ${movie.actors}", style = MaterialTheme.typography.bodyLarge)
                        Text("Rating: ${movie.rating}", style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp)
                        .clickable { expanded = !expanded }
                )
            }
        }
    }

    @Composable
    fun MovieRow(movie: Movie, onClick: () -> Unit) {
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth() // This ensures the card stretches to fill the available width
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column {
                movie.images.firstOrNull()?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Image",
                        modifier = Modifier
                            .height(200.dp) // Fixed height for all images
                            .fillMaxWidth(), // Ensures the image fills the card width
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

    @Composable
    fun MovieImagesRow(movie: Movie) {
        LazyRow {
            items(movie.images) { imageUrl ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(180.dp, 100.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Movie Image",
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

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





}
