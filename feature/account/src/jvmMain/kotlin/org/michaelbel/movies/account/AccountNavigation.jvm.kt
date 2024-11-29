package org.michaelbel.movies.account

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.michaelbel.movies.account.ui.AccountRoute

fun NavGraphBuilder.accountGraph(
    navigateBack: () -> Unit
) {
    dialog<AccountDestination>(
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        AccountRoute(
            onBackClick = navigateBack
        )
    }
}