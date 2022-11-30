package org.michaelbel.movies.feed.ui

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import java.net.UnknownHostException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.domain.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.MovieData
import org.michaelbel.movies.feed.FeedViewModel
import org.michaelbel.movies.feed.R
import org.michaelbel.movies.feed.ktx.isFailure
import org.michaelbel.movies.feed.ktx.isLoading
import org.michaelbel.movies.feed.ktx.throwable
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.ui.theme.ktx.clickableWithoutRipple

@Composable
internal fun FeedRoute(
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val pagingItems: LazyPagingItems<MovieData> = viewModel.pagingItems.collectAsLazyPagingItems()
    val isSettingsIconVisible: Boolean by viewModel.isSettingsIconVisible.collectAsStateWithLifecycle()
    val networkStatus: NetworkStatus by viewModel.networkStatus.collectAsStateWithLifecycle()

    FeedScreenContent(
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToDetails = onNavigateToDetails,
        modifier = modifier,
        pagingItems = pagingItems,
        networkStatus = networkStatus,
        isSettingsIconVisible = isSettingsIconVisible
    )
}

@Composable
internal fun FeedScreenContent(
    onNavigateToSettings: () -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    pagingItems: LazyPagingItems<MovieData>,
    networkStatus: NetworkStatus,
    isSettingsIconVisible: Boolean
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val listState: LazyListState = rememberLazyListState()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    val onScrollToTop: () -> Unit = {
        scope.launch {
            listState.animateScrollToItem(0)
        }
    }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
        }
    }

    if (pagingItems.isFailure && pagingItems.throwable is ApiKeyNotNullException) {
        onShowSnackbar(stringResource(R.string.feed_error_api_key_null))
    }

    if (networkStatus == NetworkStatus.Available && pagingItems.isFailure && pagingItems.throwable is UnknownHostException) {
        pagingItems.retry()
    }

    val resultContract = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {}

    Scaffold(
        modifier = modifier,
        topBar = {
            FeedToolbar(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(
                        horizontal = 16.dp
                    )
                    .clip(MaterialTheme.shapes.extraLarge)
                    .clickableWithoutRipple { onScrollToTop() },
                isSettingsIconVisible = isSettingsIconVisible,
                onNavigationIconClick = onNavigateToSettings
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) { paddingValues: PaddingValues ->
        when {
            pagingItems.isLoading -> {
                FeedLoading(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                )
            }
            pagingItems.isFailure -> {
                FeedFailure(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    onCheckConnectivityClick = {
                        if (Build.VERSION.SDK_INT >= 29) {
                            resultContract.launch(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
                        }
                    }
                )
            }
            else -> {
                FeedContent(
                    modifier = Modifier
                        .fillMaxSize(),
                    paddingValues = paddingValues,
                    listState = listState,
                    pagingItems = pagingItems,
                    onMovieClick = onNavigateToDetails
                )
            }
        }
    }
}