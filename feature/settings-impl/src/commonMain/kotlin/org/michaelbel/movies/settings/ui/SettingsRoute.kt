package org.michaelbel.movies.settings.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.michaelbel.movies.common.MOVIES_GITHUB_URL
import org.michaelbel.movies.common.browser.navigateToUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.interactor.entity.AppLanguage
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.settings.ktx.iconSnackbarTextRes
import org.michaelbel.movies.settings.model.SettingsData
import org.michaelbel.movies.ui.appicon.IconAlias
import org.michaelbel.movies.ui.ktx.collectAsStateCommon
import org.michaelbel.movies.ui.ktx.isDebug
import org.michaelbel.movies.ui.ktx.requestTileService
import org.michaelbel.movies.ui.lifecycle.OnResume
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.widget.ktx.rememberAndPinAppWidgetProvider

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    onRequestReview: () -> Unit,
    onRequestUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel()
) {
    val themeData by viewModel.themeData.collectAsStateCommon()
    val currentFeedView by viewModel.currentFeedView.collectAsStateCommon()
    val currentMovieList by viewModel.currentMovieList.collectAsStateCommon()
    val isBiometricFeatureAvailable by viewModel.isBiometricFeatureEnabled.collectAsStateCommon()
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsStateCommon()
    val isScreenshotBlockEnabled by viewModel.isScreenshotBlockEnabled.collectAsStateCommon()
    val appVersionData by viewModel.appVersionData.collectAsStateCommon()
    var areNotificationsEnabled by remember { mutableStateOf(viewModel.areNotificationsEnabled) }
    val openAppNotificationSettings = viewModel.settingsUiInteractor.navigateToAppNotificationSettings()
    val navigateToUrl = navigateToUrl(MOVIES_GITHUB_URL)

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val permissionMessage = stringResource(MoviesStrings.settings_post_notifications_should_request)
    val permissionAction = stringResource(MoviesStrings.settings_action_go)
    val onShowPermissionSnackbar: () -> Unit = {
        scope.launch {
            val result = snackbarHostState.showSnackbar(
                message = permissionMessage,
                actionLabel = permissionAction,
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                openAppNotificationSettings()
            }
        }
    }

    val onShowSnackbar: (String) -> Unit = { message ->
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    val messageRed = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Red.iconSnackbarTextRes))
    val messagePurple = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Purple.iconSnackbarTextRes))
    val messageBrown = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Brown.iconSnackbarTextRes))
    val messageAmoled = stringResource(MoviesStrings.settings_app_launcher_icon_changed_to, stringResource(IconAlias.Amoled.iconSnackbarTextRes))

    SettingsScreenContent(
        settingsData = SettingsData(
            onBackClick = onBackClick,
            languageData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isLanguageFeatureEnabled,
                current = AppLanguage.transform(stringResource(MoviesStrings.language_code)),
                onSelect = viewModel::selectLanguage
            ),
            themeData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isThemeFeatureEnabled,
                current = themeData.appTheme,
                onSelect = viewModel::selectTheme
            ),
            feedViewData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isFeedViewFeatureEnabled,
                current = currentFeedView,
                onSelect = viewModel::selectFeedView
            ),
            movieListData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isMovieListFeatureEnabled,
                current = currentMovieList,
                onSelect = viewModel::selectMovieList
            ),
            genderData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isGenderFeatureEnabled,
                current = viewModel.settingsUiInteractor.grammaticalGender,
                onSelect = { gender -> viewModel.settingsUiInteractor.setGrammaticalGender(GrammaticalGender.value(gender)) }
            ),
            dynamicColorsData = SettingsData.ChangedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isDynamicColorsFeatureEnabled,
                isEnabled = themeData.dynamicColors,
                onChange = viewModel::setDynamicColors
            ),
            paletteColorsData = SettingsData.PaletteColorsData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isPaletteColorsFeatureEnabled,
                isDynamicColorsEnabled = themeData.dynamicColors,
                paletteKey = themeData.paletteKey,
                seedColor = themeData.seedColor,
                onChange = { localDynamicColors, localPaletteKey, localSeedColor ->
                    viewModel.run {
                        setDynamicColors(localDynamicColors)
                        setPaletteKey(localPaletteKey)
                        setSeedColor(localSeedColor)
                    }
                }
            ),
            notificationsData = SettingsData.NotificationsData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isNotificationsFeatureEnabled,
                isEnabled = areNotificationsEnabled,
                onClick = viewModel.settingsUiInteractor.rememberPostNotificationsPermissionHandler(
                    areNotificationsEnabled = areNotificationsEnabled,
                    onPermissionGranted = { areNotificationsEnabled = viewModel.areNotificationsEnabled },
                    onPermissionDenied = onShowPermissionSnackbar
                )
            ),
            biometricData = SettingsData.ChangedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isBiometricFeatureEnabled && isBiometricFeatureAvailable,
                isEnabled = isBiometricEnabled,
                onChange = viewModel::setBiometricEnabled
            ),
            widgetData = SettingsData.RequestedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isWidgetFeatureEnabled,
                onRequest = rememberAndPinAppWidgetProvider()
            ),
            tileData = SettingsData.RequestedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isTileFeatureEnabled,
                onRequest = requestTileService(onShowSnackbar)
            ),
            appIconData = SettingsData.ListData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isAppIconFeatureEnabled,
                current = viewModel.settingsUiInteractor.enabledIcon,
                onSelect = { icon ->
                    val message = when (icon) {
                        IconAlias.Red -> messageRed
                        IconAlias.Purple -> messagePurple
                        IconAlias.Brown -> messageBrown
                        IconAlias.Amoled -> messageAmoled
                    }
                    onShowSnackbar(message)
                    viewModel.settingsUiInteractor.setIcon(icon)
                }
            ),
            screenshotData = SettingsData.ChangedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isScreenshotFeatureEnabled,
                isEnabled = isScreenshotBlockEnabled,
                onChange = viewModel::setScreenshotBlockEnabled
            ),
            githubData = SettingsData.RequestedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isGithubFeatureEnabled,
                onRequest = navigateToUrl
            ),
            reviewAppData = SettingsData.RequestedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isReviewAppFeatureEnabled && viewModel.isReviewFeatureEnabled,
                onRequest = onRequestReview
            ),
            updateAppData = SettingsData.RequestedData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isUpdateAppFeatureEnabled && viewModel.isUpdateFeatureEnabled && viewModel.isUpdateAvailable,
                onRequest = onRequestUpdate
            ),
            aboutData = SettingsData.AboutData(
                isFeatureEnabled = viewModel.settingsUiInteractor.isAboutFeatureEnabled,
                versionName = viewModel.aboutInteractor.versionName,
                versionCode = viewModel.aboutInteractor.versionCode,
                flavor = appVersionData.flavor,
                isDebug = isDebug
            )
        ),
        windowInsets = viewModel.settingsUiInteractor.settingsWindowInsets,
        snackbarHostState = snackbarHostState,
        isNavigationIconVisible = viewModel.settingsUiInteractor.isNavigationIconVisible,
        bottomBarModifier = viewModel.settingsUiInteractor.bottomBarModifier,
        modifier = modifier
    )

    OnResume {
        areNotificationsEnabled = viewModel.areNotificationsEnabled
    }
}