package org.michaelbel.movies.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun ApiKeyBox(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(MoviesStrings.error_api_key_null),
        modifier = modifier,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge.copy(MaterialTheme.colorScheme.error)
    )
}

@Preview
@Composable
private fun ApiKeyBoxPreview() {
    MoviesTheme {
        ApiKeyBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}