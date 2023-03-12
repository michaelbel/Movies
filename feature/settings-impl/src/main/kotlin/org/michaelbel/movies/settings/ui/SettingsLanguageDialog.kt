package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.settings.ktx.languageText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.LanguagePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SettingLanguageDialog(
    languages: List<AppLanguage>,
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },
        icon = {
            Icon(
                imageVector = MoviesIcons.Language,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = stringResource(R.string.settings_language),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            SettingLanguageDialogContent(
                languages = languages,
                currentLanguage = currentLanguage,
                onLanguageSelect = { language ->
                    onLanguageSelect(language)
                    onDismissRequest()
                }
            )
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    )
}

@Composable
private fun SettingLanguageDialogContent(
    languages: List<AppLanguage>,
    currentLanguage: AppLanguage,
    onLanguageSelect: (AppLanguage) -> Unit
) {
    Column {
        languages.forEach { language: AppLanguage ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onLanguageSelect(language)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = currentLanguage == language,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F)
                    ),
                    modifier = Modifier
                        .padding(
                            start = 16.dp
                        )
                )

                Text(
                    text = language.languageText,
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        ),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@DevicePreviews
private fun SettingLanguageDialogPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) language: AppLanguage
) {
    MoviesTheme {
        SettingLanguageDialog(
            languages = listOf(
                AppLanguage.English,
                AppLanguage.Russian
            ),
            currentLanguage = language,
            onLanguageSelect = {},
            onDismissRequest = {}
        )
    }
}