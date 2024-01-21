package org.michaelbel.movies.search.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.common.exceptions.PageEmptyException
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.persistence.database.entity.MovieDb
import org.michaelbel.movies.persistence.database.entity.SuggestionDb
import org.michaelbel.movies.search.SearchViewModel
import org.michaelbel.movies.ui.compose.page.PageContent
import org.michaelbel.movies.ui.compose.page.PageFailure
import org.michaelbel.movies.ui.compose.page.PageLoading
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.ktx.displayCutoutWindowInsets
import org.michaelbel.movies.ui.ktx.isFailure
import org.michaelbel.movies.ui.ktx.isLoading
import org.michaelbel.movies.ui.ktx.throwable
import org.michaelbel.movies.ui.R as UiR

@Composable
fun SearchRoute(
    onBackClick: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<MovieDb> = viewModel.pagingItems.collectAsLazyPagingItems()
    val currentFeedView: FeedView by viewModel.currentFeedView.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()
    val suggestions: List<SuggestionDb> by viewModel.suggestionsFlow.collectAsStateWithLifecycle()
    val searchHistoryMovies: List<MovieDb> by viewModel.searchHistoryMoviesFlow.collectAsStateWithLifecycle()
    val active: Boolean by viewModel.active.collectAsStateWithLifecycle()

    SearchScreenContent(
        pagingItems = pagingItems,
        networkStatus = networkStatus,
        currentFeedView = currentFeedView,
        suggestions = suggestions,
        searchHistoryMovies = searchHistoryMovies,
        onBackClick = onBackClick,
        onNavigateToDetails = onNavigateToDetails,
        onChangeSearchQuery = viewModel::onChangeSearchQuery,
        onSaveMovieToHistory = viewModel::onSaveToHistory,
        onRemoveMovieFromHistory = viewModel::onRemoveFromHistory,
        onHistoryClear = viewModel::onClearSearchHistory,
        active = active,
        onChangeActiveState = viewModel::onChangeActiveState,
        modifier = modifier
    )
}

@Composable
private fun SearchScreenContent(
    pagingItems: LazyPagingItems<MovieDb>,
    networkStatus: NetworkStatus,
    currentFeedView: FeedView,
    suggestions: List<SuggestionDb>,
    searchHistoryMovies: List<MovieDb>,
    onBackClick: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    onChangeSearchQuery: (String) -> Unit,
    onSaveMovieToHistory: (Int) -> Unit,
    onRemoveMovieFromHistory: (Int) -> Unit,
    onHistoryClear: () -> Unit,
    active: Boolean,
    onChangeActiveState: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val lazyListState: LazyListState = rememberLazyListState()
    val lazyGridState: LazyGridState = rememberLazyGridState()
    val lazyStaggeredGridState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val focusRequester: FocusRequester = remember { FocusRequester() }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (pagingItems.isFailure && pagingItems.throwable is ApiKeyNotNullException) {
        onShowSnackbar(stringResource(UiR.string.error_api_key_null))
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.throwable is UnknownHostException) {
        pagingItems.retry()
    }

    var query: String by rememberSaveable { mutableStateOf("") }

    val searchBarHorizontalPadding: Dp by animateDpAsState(
        targetValue = if (active) 0.dp else 8.dp,
        label = ""
    )

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { innerPadding ->
        Column {
            SearchToolbar(
                query = query,
                onQueryChange = { text ->
                    query = text
                },
                onSearch = {
                    onChangeSearchQuery(query)
                    onChangeActiveState(false)
                },
                active = active,
                onActiveChange = { state ->
                    onChangeActiveState(state)
                },
                onBackClick = onBackClick,
                onCloseClick = {
                    onChangeActiveState(query.isNotEmpty())
                    query = ""
                },
                onInputText = { text ->
                    query = text
                    onChangeSearchQuery(text)
                },
                suggestions = suggestions,
                onSuggestionClick = { suggestion ->
                    query = suggestion.title
                    onChangeSearchQuery(suggestion.title)
                    onChangeActiveState(false)
                },
                searchHistoryMovies = searchHistoryMovies,
                onHistoryMovieClick = { title ->
                    query = title
                    onChangeSearchQuery(title)
                    onChangeActiveState(false)
                },
                onHistoryMovieRemoveClick = onRemoveMovieFromHistory,
                onClearHistoryClick = onHistoryClear,
                modifier = Modifier
                    .padding(horizontal = searchBarHorizontalPadding)
                    .windowInsetsPadding(displayCutoutWindowInsets)
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )

            when {
                pagingItems.isLoading -> {
                    PageLoading(
                        feedView = currentFeedView,
                        modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                    )
                }
                pagingItems.isFailure -> {
                    if (pagingItems.throwable is PageEmptyException) {
                        SearchEmpty(
                            modifier = Modifier
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                        )
                    } else {
                        PageFailure(
                            modifier = Modifier
                                .padding(innerPadding)
                                .windowInsetsPadding(displayCutoutWindowInsets)
                                .fillMaxSize()
                                .clickableWithoutRipple(pagingItems::retry)
                        )
                    }
                }
                else -> {
                    PageContent(
                        feedView = currentFeedView,
                        lazyListState = lazyListState,
                        lazyGridState = lazyGridState,
                        lazyStaggeredGridState = lazyStaggeredGridState,
                        pagingItems = pagingItems,
                        onMovieClick = { movieId ->
                            onSaveMovieToHistory(movieId)
                            onNavigateToDetails(movieId)
                        },
                        modifier = Modifier.windowInsetsPadding(displayCutoutWindowInsets)
                    )
                }
            }
        }
    }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }
}