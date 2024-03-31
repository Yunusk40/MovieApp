package com.example.movieappmad24.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.models.getMovies
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie

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
