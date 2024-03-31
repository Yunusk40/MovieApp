package com.example.movieappmad24.Screen
sealed class Screen(val route: String) {
    object MovieList : Screen("movieList")
    object DetailScreen : Screen("detailScreen/{movieId}") {
        fun createRoute(movieId: String) = "detailScreen/$movieId"
    }
    object WatchlistScreen : Screen("watchlistScreen")
}

