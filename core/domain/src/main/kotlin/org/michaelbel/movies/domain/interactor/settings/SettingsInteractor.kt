package org.michaelbel.movies.domain.interactor.settings

import androidx.compose.ui.unit.LayoutDirection
import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData

interface SettingsInteractor {

    val currentTheme: Flow<AppTheme>

    val dynamicColors: Flow<Boolean>

    val layoutDirection: Flow<LayoutDirection>

    val isSettingsIconVisible: Flow<Boolean>

    val isPlayServicesAvailable: Flow<Boolean>

    val isAppFromGooglePlay: Flow<Boolean>

    val appVersionData: Flow<AppVersionData>

    suspend fun selectTheme(theme: AppTheme)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setRtlEnabled(value: Boolean)

    suspend fun fetchRemoteConfig()
}