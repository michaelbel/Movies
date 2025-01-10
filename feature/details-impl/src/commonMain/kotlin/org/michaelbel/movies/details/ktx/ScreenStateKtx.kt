package org.michaelbel.movies.details.ktx

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.network.config.ScreenState
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.url
import org.michaelbel.movies.ui.strings.MoviesStrings

internal val ScreenState.Content<*>.movie: MoviePojo
    get() = data as MoviePojo

internal val ScreenState.toolbarTitle: String
    @Composable get() = when (this) {
        is ScreenState.Loading -> stringResource(MoviesStrings.details_title)
        is ScreenState.Content<*> -> movie.title
        is ScreenState.Failure -> stringResource(MoviesStrings.details_title)
    }

internal val ScreenState.movieUrl: String?
    get() = if (this is ScreenState.Content<*>) movie.url else null

@Composable
internal fun ScreenState.primaryContainer(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Content<*> -> if (movie.containerColor != null && !isAmoledTheme) Color(requireNotNull(movie.containerColor)) else MaterialTheme.colorScheme.primaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.primaryContainer
    }
}

@Composable
internal fun ScreenState.onPrimaryContainer(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Content<*> -> if (movie.onContainerColor != null && !isAmoledTheme) Color(requireNotNull(movie.onContainerColor)) else MaterialTheme.colorScheme.onPrimaryContainer
        is ScreenState.Failure -> MaterialTheme.colorScheme.onPrimaryContainer
    }
}

@Composable
internal fun ScreenState.scrolledContainerColor(isAmoledTheme: Boolean): Color {
    return when (this) {
        is ScreenState.Loading -> MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Content<*> -> if (movie.containerColor != null && !isAmoledTheme) Color.Transparent else MaterialTheme.colorScheme.inversePrimary
        is ScreenState.Failure -> MaterialTheme.colorScheme.inversePrimary
    }
}