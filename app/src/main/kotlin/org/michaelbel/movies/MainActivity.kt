package org.michaelbel.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.domain.workers.AccountUpdateWorker
import org.michaelbel.movies.domain.workers.MoviesDatabaseWorker
import org.michaelbel.movies.ui.shortcuts.installShortcuts
import org.michaelbel.movies.ui.theme.MoviesTheme

/**
 * Per-App Language depends on AppCompatActivity (not ComponentActivity).
 */
@AndroidEntryPoint
internal class MainActivity: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        /** Configure edge-to-edge display. */
        WindowCompat.setDecorFitsSystemWindows(window, false)

        installShortcuts()
        setContent {
            val currentTheme: AppTheme by viewModel.currentTheme.collectAsStateWithLifecycle()
            val dynamicColors: Boolean by viewModel.dynamicColors.collectAsStateWithLifecycle()
            val layoutDirection: LayoutDirection by viewModel.layoutDirection.collectAsStateWithLifecycle()

            val navHostController: NavHostController = rememberNavController().apply {
                addOnDestinationChangedListener { _, destination, arguments ->
                    viewModel.analyticsTrackDestination(destination, arguments)
                }
            }

            MoviesTheme(
                theme = currentTheme,
                dynamicColors = dynamicColors
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    MainActivityContent(
                        navHostController = navHostController
                    )
                }
            }
        }

        prepopulateDatabase()
        updateAccountDetails()
    }

    private fun prepopulateDatabase() {
        val request = OneTimeWorkRequestBuilder<MoviesDatabaseWorker>()
            .setInputData(workDataOf(MoviesDatabaseWorker.KEY_FILENAME to MOVIES_DATA_FILENAME))
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private fun updateAccountDetails() {
        val request = OneTimeWorkRequestBuilder<AccountUpdateWorker>()
            .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    private companion object {
        private const val MOVIES_DATA_FILENAME = "movies.json"
    }
}