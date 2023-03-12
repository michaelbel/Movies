package org.michaelbel.movies.settings

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import org.michaelbel.movies.navigation.MoviesNavigationDestination
import org.michaelbel.movies.settings.ui.SettingsRoute
import org.michaelbel.movies.ui.shortcuts.INTENT_ACTION_SETTINGS

private val SETTINGS_SHORTCUT_NAV_DEEP_LINK: NavDeepLink = navDeepLink {
    uriPattern = INTENT_ACTION_SETTINGS
}

object SettingsDestination: MoviesNavigationDestination {

    override val route: String = "settings"

    override val destination: String = "settings"
}

fun NavGraphBuilder.settingsGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = SettingsDestination.route,
        deepLinks = listOf(SETTINGS_SHORTCUT_NAV_DEEP_LINK)
    ) {
        SettingsRoute(
            onBackClick = onBackClick
        )
    }
}