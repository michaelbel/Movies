package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.michaelbel.movies.common.browser.navigateToUrl
import org.michaelbel.movies.network.config.TMDB_PRIVACY_POLICY
import org.michaelbel.movies.network.config.TMDB_TERMS_OF_USE
import org.michaelbel.movies.ui.ktx.clickableWithoutRipple
import org.michaelbel.movies.ui.strings.MoviesStrings
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
fun AuthLinksBox(
    modifier: Modifier = Modifier
) {
    val navigateToTermsOfUseUrl = navigateToUrl(TMDB_TERMS_OF_USE)
    val navigateToPrivacyPolicyUrl = navigateToUrl(TMDB_PRIVACY_POLICY)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            thickness = .1.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(MoviesStrings.auth_terms_of_use),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple(navigateToTermsOfUseUrl),
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(3.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer)
            )

            Text(
                text = stringResource(MoviesStrings.auth_privacy_policy),
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickableWithoutRipple(navigateToPrivacyPolicyUrl),
                style = MaterialTheme.typography.bodyMedium.copy(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Preview
@Composable
private fun AuthLinksBoxPreview() {
    MoviesTheme {
        AuthLinksBox(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
        )
    }
}