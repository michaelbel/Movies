package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.auth_impl.R
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.preview.DevicePreviews
import org.michaelbel.movies.ui.theme.AmoledTheme
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AuthLinksBox(
    onTermsOfUseClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            thickness = .5.dp
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.auth_terms_of_use),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple { onTermsOfUseClick() },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Text(
                text = stringResource(R.string.auth_privacy_policy),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple { onPrivacyPolicyClick() },
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}

@Composable
@DevicePreviews
private fun AuthLinksBoxPreview() {
    MoviesTheme {
        AuthLinksBox(
            onTermsOfUseClick = {},
            onPrivacyPolicyClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}

@Composable
@Preview
private fun AuthLinksBoxAmoledPreview() {
    AmoledTheme {
        AuthLinksBox(
            onTermsOfUseClick = {},
            onPrivacyPolicyClick = {},
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}