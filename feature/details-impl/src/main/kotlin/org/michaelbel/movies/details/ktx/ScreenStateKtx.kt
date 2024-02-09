package org.michaelbel.movies.details.ktx

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.details_impl.R
import org.michaelbel.movies.network.ScreenState
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.ktx.url

internal val ScreenState.Content<*>.movie: MovieDb
    get() = data as MovieDb

internal val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> stringResource(R.string.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> stringResource(R.string.details_title)
    }

internal val ScreenState.movieUrl: String?
    get() = if (this is ScreenState.Content<*>) movie.url else null

internal val ScreenState.primaryContainer: Color
    @Composable get() = when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Content<*> -> if (movie.containerColor != null) Color(requireNotNull(movie.containerColor)) else MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.primaryContainer
    }

internal val ScreenState.onPrimaryContainer: Color
    @Composable get() = when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Content<*> -> if (movie.onContainerColor != null) Color(requireNotNull(movie.onContainerColor)) else MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.onPrimaryContainer
    }

internal val ScreenState.scrolledContainerColor: Color
    @Composable get() = when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Content<*> -> if (movie.containerColor != null) Color.Transparent else MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Failure -> MaterialTheme.colorScheme.inversePrimary
    }