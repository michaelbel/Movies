package org.michaelbel.movies

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.michaelbel.movies.account.accountGraph
import org.michaelbel.movies.account.navigateToAccount
import org.michaelbel.movies.auth.authGraph
import org.michaelbel.movies.auth.navigateToAuth
import org.michaelbel.movies.details.detailsGraph
import org.michaelbel.movies.details.navigateToDetails
import org.michaelbel.movies.feed.feedGraph
import org.michaelbel.movies.gallery.galleryGraph
import org.michaelbel.movies.gallery.navigateToGallery
import org.michaelbel.movies.search.navigateToSearch
import org.michaelbel.movies.search.searchGraph
import org.michaelbel.movies.settings.navigateToSettings
import org.michaelbel.movies.settings.settingsGraph

@Composable
internal fun MainWindowContent(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = "feed"
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