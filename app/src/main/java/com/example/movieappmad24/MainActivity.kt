package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.models.getMovies
import coil.compose.AsyncImage

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
            Scaffold (
                topBar = {MovieTopAppBar()},
                bottomBar = {BottomNavigationBar()}
            ) { innerPadding ->
                MovieCardContent(innerPadding)
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
    fun BottomNavigationBar() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Handle Home Click */ }) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Handle Watchlist Click */ }) {
                Icon(Icons.Filled.Star, contentDescription = "Watchlist")
            }
        }
    }

    @Composable
    fun MovieCardContent(paddingValues: PaddingValues) {
        val movies = getMovies() // Fetch movies from your movies.kt file
        Column(modifier = Modifier.padding(paddingValues)) {
            movies.forEach { movie ->
                ExpandableMovieCard(movie = movie, padding = PaddingValues(all = 8.dp))
            }
        }
    }

    //shows content of movie and image
    @Composable
    fun ExpandableMovieCard(movie: Movie, padding: PaddingValues) {
        var expanded by remember { mutableStateOf(false) }
        val paddingValue = animateDpAsState(targetValue = if (expanded) 16.dp else 8.dp).value

        Card(
            modifier = Modifier
                .padding(padding)
                .clickable { expanded = !expanded },
        ) {
            Column(
                modifier = Modifier.padding(paddingValue),
                horizontalAlignment = Alignment.Start
            ) {
                // Loading the first image from the movie images list
                AsyncImage(
                    model = movie.images.firstOrNull(), // Use the first image URL or null if the list is empty
                    contentDescription = "Movie Image",
                    modifier = Modifier
                        .height(200.dp) // Specify height, adjust as needed
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop // Fill width, crop as needed
                )
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                AnimatedVisibility(expanded) {
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
                    modifier = Modifier.align(Alignment.End).clickable { expanded = !expanded }
                )
            }
        }
    }


}
