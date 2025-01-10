@file:OptIn(ExperimentalMaterial3Api::class)

package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.compose.iconbutton.CloseIcon
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AuthToolbar(
    modifier: Modifier = Modifier,
    onNavigationIconClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(MoviesStrings.auth_title),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        },
        modifier = modifier,
        navigationIcon = {
            CloseIcon(
                onClick = onNavigationIconClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent)
    )
}

@Preview
@Composable
private fun AuthToolbarPreview() {
    MoviesTheme {
        AuthToolbar(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
            onNavigationIconClick = {}
        )
    }
}