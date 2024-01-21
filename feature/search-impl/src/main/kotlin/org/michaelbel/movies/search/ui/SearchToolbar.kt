package org.michaelbel.movies.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.compose.iconbutton.BackIcon
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.compose.iconbutton.VoiceIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.MoviePreviewParameterProvider
import org.michaelbel.movies.ui.preview.provider.SuggestionDbPreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun SearchToolbar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit,
    onInputText: (String) -> Unit,
    suggestions: List<SuggestionDb>,
    onSuggestionClick: (SuggestionDb) -> Unit,
    searchHistoryMovies: List<MovieDb>,
    onHistoryMovieClick: (String) -> Unit,
    onHistoryMovieRemoveClick: (Int) -> Unit,
    onClearHistoryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier,
        placeholder = {
            Text(
                text = stringResource(R.string.search_title)
            )
        },
        leadingIcon = {
            if (active) {
                Icon(
                    imageVector = MoviesIcons.Search,
                    contentDescription = null
                )
            } else {
                BackIcon(
                    onClick = onBackClick
                )
            }
        },
        trailingIcon = {
            if (active) {
                CloseIcon(
                    onClick = onCloseClick
                )
            } else {
                VoiceIcon(
                    onInputText = onInputText
                )
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.inversePrimary
        )
    ) {
        Divider(
            thickness = .1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        when {
            searchHistoryMovies.isNotEmpty() -> {
                Column(
                    modifier = Modifier.fillMaxSize().imePadding()
                ) {
                    SearchHistoryHeader(
                        onClearButtonClick = onClearHistoryClick,
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    )

                    LazyColumn {
                        items(searchHistoryMovies) { movie ->
                            SearchRecentResult(
                                text = movie.title,
                                onRemoveClick = { onHistoryMovieRemoveClick(movie.movieId) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .clickable { onHistoryMovieClick(movie.title) }
                            )
                        }
                    }
                }
            }
            suggestions.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize().imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn {
                        items(suggestions) { suggestion ->
                            SearchSuggestion(
                                text = suggestion.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp)
                                    .clickable { onSuggestionClick(suggestion) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SearchToolbarPreview(
    @PreviewParameter(SuggestionDbPreviewParameterProvider::class) suggestions: List<SuggestionDb>
) {
    MoviesTheme {
        SearchToolbar(
            query = "Napoleon",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {},
            onBackClick = {},
            onCloseClick = {},
            onInputText = {},
            suggestions = suggestions,
            onSuggestionClick = {},
            searchHistoryMovies = emptyList(),
            onHistoryMovieClick = {},
            onHistoryMovieRemoveClick = {},
            onClearHistoryClick = {}
        )
    }
}

@Composable
@DevicePreviews
private fun SearchToolbarPreview2(
    @PreviewParameter(MoviePreviewParameterProvider::class) movies: List<MovieDb>
) {
    MoviesTheme {
        SearchToolbar(
            query = "Napoleon",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {},
            onBackClick = {},
            onCloseClick = {},
            onInputText = {},
            suggestions = emptyList(),
            onSuggestionClick = {},
            searchHistoryMovies = movies,
            onHistoryMovieClick = {},
            onHistoryMovieRemoveClick = {},
            onClearHistoryClick = {}
        )
    }
}