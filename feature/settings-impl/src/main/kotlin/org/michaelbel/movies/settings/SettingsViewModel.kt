package org.michaelbel.movies.settings

import android.os.Build
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.DefaultLifecycleObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import org.michaelbel.movies.domain.usecase.DelayUseCase
import org.michaelbel.movies.domain.usecase.SelectLanguageCase
import org.michaelbel.movies.domain.usecase.SelectThemeCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsInteractor: SettingsInteractor,
    private val selectLanguageCase: SelectLanguageCase,
    private val selectThemeCase: SelectThemeCase,
    private val delayUseCase: DelayUseCase
): BaseViewModel(), DefaultLifecycleObserver {

    val isDynamicColorsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 31

    val isRtlFeatureEnabled: Boolean = false

    val isPostNotificationsFeatureEnabled: Boolean = Build.VERSION.SDK_INT >= 33

    val languages: List<AppLanguage> = listOf(
        AppLanguage.English,
        AppLanguage.Russian
    )

    val themes: List<AppTheme> = listOf(
        AppTheme.NightNo,
        AppTheme.NightYes,
        AppTheme.FollowSystem
    )

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

    val layoutDirection: StateFlow<LayoutDirection> = settingsInteractor.layoutDirection
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = LayoutDirection.Ltr
        )

    val isPlayServicesAvailable: StateFlow<Boolean> = settingsInteractor.isPlayServicesAvailable
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val isAppFromGooglePlay: StateFlow<Boolean> = settingsInteractor.isAppFromGooglePlay
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = false
        )

    val networkRequestDelay: StateFlow<Int> = delayUseCase.networkRequestDelay
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = 0
        )

    val appVersionData: StateFlow<AppVersionData> = settingsInteractor.appVersionData
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AppVersionData.None
        )

    fun selectLanguage(language: AppLanguage) = launch {
        selectLanguageCase(language)
    }

    fun selectTheme(theme: AppTheme) = launch {
        selectThemeCase(theme)
    }

    fun setDynamicColors(value: Boolean) = launch {
        settingsInteractor.setDynamicColors(value)
    }

    fun setRtlEnabled(value: Boolean) = launch {
        settingsInteractor.setRtlEnabled(value)
    }

    fun setNetworkRequestDelay(value: Int) = launch {
        delayUseCase(value)
    }
}