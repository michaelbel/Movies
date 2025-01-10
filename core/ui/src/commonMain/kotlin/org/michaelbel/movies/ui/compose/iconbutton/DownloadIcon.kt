package org.michaelbel.movies.ui.compose.iconbutton

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun DownloadIcon(
    onClick: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            imageVector = MoviesIcons.FileDownload,
            contentDescription = stringResource(MoviesContentDescriptionCommon.DownloadIcon),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

@Preview
@Composable
private fun DownloadIconPreview() {
    MoviesTheme {
        DownloadIcon(
            onClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}