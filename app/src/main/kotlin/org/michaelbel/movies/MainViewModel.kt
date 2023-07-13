package org.michaelbel.movies

import android.os.Bundle
import androidx.navigation.NavDestination
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.analytics.MoviesAnalytics
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.domain.workers.AccountUpdateWorker
import org.michaelbel.movies.domain.workers.MoviesDatabaseWorker
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val workManager: WorkManager,
    private val analytics: MoviesAnalytics
): BaseViewModel() {

    val currentTheme: StateFlow<AppTheme> = settingsInteractor.currentTheme
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppTheme.FollowSystem
        )

    val dynamicColors: StateFlow<Boolean> = settingsInteractor.dynamicColors
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    init {
        fetchRemoteConfig()
        prepopulateDatabase()
        updateAccountDetails()
    }

    fun analyticsTrackDestination(destination: NavDestination, arguments: Bundle?) {
        analytics.trackDestination(destination.route, arguments)
    }

    private fun fetchRemoteConfig() = launch {
        settingsInteractor.fetchRemoteConfig()
    }

    private fun prepopulateDatabase() {
        val request = OneTimeWorkRequestBuilder<MoviesDatabaseWorker>()
            .setInputData(workDataOf(MoviesDatabaseWorker.KEY_FILENAME to MOVIES_DATA_FILENAME))
            .build()
        workManager.enqueue(request)
    }

    private fun updateAccountDetails() {
        val request = OneTimeWorkRequestBuilder<AccountUpdateWorker>()
            .build()
        workManager.enqueue(request)
    }

    private companion object {
        private const val MOVIES_DATA_FILENAME = "movies.json"
    }
}