package org.michaelbel.movies.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.search_impl.R
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun SearchHistoryHeader(
    onClearButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.search_recent),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        TextButton(
            onClick = onClearButtonClick
        ) {
            Text(
                text = stringResource(R.string.search_clear)
            )
        }
    }

    /*ConstraintLayout(
        modifier = modifier
    ) {
        val (recentSearchesText, clearButton) = createRefs()

        Text(
            text = stringResource(R.string.search_recent),
            modifier = Modifier.constrainAs(recentSearchesText) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                start.linkTo(parent.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
        )

        TextButton(
            onClick = onClearButtonClick,
            modifier = Modifier.constrainAs(clearButton) {
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                end.linkTo(parent.end, 8.dp)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = stringResource(R.string.search_clear)
            )
        }
    }*/
}

@Preview
@Composable
private fun SearchHistoryHeaderPreview() {
    MoviesTheme {
        SearchHistoryHeader(
            onClearButtonClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}