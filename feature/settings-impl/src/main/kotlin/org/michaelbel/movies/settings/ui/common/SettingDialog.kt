package org.michaelbel.movies.settings.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.ktx.stringText
import org.michaelbel.movies.settings_impl.R
import org.michaelbel.movies.ui.accessibility.MoviesContentDescription
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.preview.provider.AppearancePreviewParameterProvider
import org.michaelbel.movies.ui.preview.provider.LanguagePreviewParameterProvider
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun <T: SealedString> SettingsDialog(
    icon: ImageVector,
    title: String,
    items: List<T>,
    currentItem: T,
    onItemSelect: (T) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("ConfirmTextButton")
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    modifier = Modifier.testTag("ConfirmText"),
                    style = MaterialTheme.typography.labelLarge.copy(MaterialTheme.colorScheme.primary)
                )
            }
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(MoviesContentDescription.AppearanceIcon),
                modifier = Modifier.testTag("Icon")
            )
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.testTag("Title"),
                style = MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.onSurface)
            )
        },
        text = {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clickable {
                                onItemSelect(item)
                                onDismissRequest()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentItem == item,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .6F)
                            ),
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Text(
                            text = item.stringText,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
internal fun <T: SealedString> SettingsDialog(
    icon: Painter,
    title: String,
    items: List<T>,
    currentItem: T,
    onItemSelect: (T) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onDismissRequest,
                modifier = Modifier.testTag("ConfirmTextButton")
            ) {
                Text(
                    text = stringResource(R.string.settings_action_cancel),
                    modifier = Modifier.testTag("ConfirmText"),
                    style = MaterialTheme.typography.labelLarge.copy(MaterialTheme.colorScheme.primary)
                )
            }
        },
        icon = {
            Icon(
                painter = icon,
                contentDescription = stringResource(MoviesContentDescription.AppearanceIcon),
                modifier = Modifier.testTag("Icon")
            )
        },
        title = {
            Text(
                text = title,
                modifier = Modifier.testTag("Title"),
                style = MaterialTheme.typography.headlineSmall.copy(MaterialTheme.colorScheme.onSurface)
            )
        },
        text = {
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clickable {
                                onItemSelect(item)
                                onDismissRequest()
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = currentItem == item,
                            onClick = null,
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .6F)
                            ),
                            modifier = Modifier.padding(start = 16.dp)
                        )

                        Text(
                            text = item.stringText,
                            modifier = Modifier.padding(start = 8.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        },
        shape = RoundedCornerShape(28.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
@DevicePreviews
private fun SettingDialogPreview(
    @PreviewParameter(AppearancePreviewParameterProvider::class) appLanguage: AppLanguage
) {
    MoviesTheme {
        SettingsDialog(
            icon = MoviesIcons.Language,
            title = stringResource(R.string.settings_language),
            items = AppLanguage.VALUES,
            currentItem = appLanguage,
            onItemSelect = {},
            onDismissRequest = {}
        )
    }
}

@Composable
@Preview
private fun SettingDialogAmoledPreview(
    @PreviewParameter(LanguagePreviewParameterProvider::class) appLanguage: AppLanguage
) {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        SettingsDialog(
            icon = MoviesIcons.Language,
            title = stringResource(R.string.settings_language),
            items = AppLanguage.VALUES,
            currentItem = appLanguage,
            onItemSelect = {},
            onDismissRequest = {}
        )
    }
}