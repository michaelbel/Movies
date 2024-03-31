package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.michaelbel.movies.account.accountGraph
import org.michaelbel.movies.account.navigateToAccount
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.navigateToAuth
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.feed.FeedDestination
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.gallery.navigateToGallery
import org.michaelbel.movies.navigation.ktx.addOnDestinationChangedListener
import org.michaelbel.movies.search.navigateToSearch
import org.michaelbel.movies.search.searchGraph
import org.michaelbel.movies.settings.navigateToSettings
import org.michaelbel.movies.settings.settingsGraph
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun MainActivityContent(
    viewModel: MainViewModel = koinViewModel(),
    enableEdgeToEdge: (Any, Any) -> Unit
) {
    val themeData by viewModel.themeData.collectAsStateWithLifecycle()
    val navHostController = rememberNavController().apply {
        addOnDestinationChangedListener(viewModel::analyticsTrackDestination)
    }

    MoviesTheme(
        themeData = themeData,
        enableEdgeToEdge = enableEdgeToEdge
    ) {
        NavHost(
            navController = navHostController,
            startDestination = FeedDestination.route
        ) {
            authGraph(
                navigateBack = navHostController::popBackStack
            )
            accountGraph(
                navigateBack = navHostController::popBackStack
            )
            feedGraph(
                navigateToSearch = navHostController::navigateToSearch,
                navigateToAuth = navHostController::navigateToAuth,
                navigateToAccount = navHostController::navigateToAccount,
                navigateToSettings = navHostController::navigateToSettings,
                navigateToDetails = navHostController::navigateToDetails
            )
            detailsGraph(
                navigateBack = navHostController::popBackStack,
                navigateToGallery = navHostController::navigateToGallery
            )
            galleryGraph(
                navigateBack = navHostController::popBackStack
            )
            searchGraph(
                navigateBack = navHostController::popBackStack,
                navigateToDetails = navHostController::navigateToDetails,
            )
            settingsGraph(
                navigateBack = navHostController::popBackStack
            )
        }
    }
}