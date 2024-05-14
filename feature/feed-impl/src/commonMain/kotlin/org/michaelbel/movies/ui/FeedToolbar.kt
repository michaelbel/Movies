@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)

package org.michaelbel.movies.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.isEmpty
import org.michaelbel.movies.ui.accessibility.MoviesContentDescriptionCommon
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.compose.ApiKeyBox
import org.michaelbel.movies.ui.compose.iconbutton.SearchIcon
import org.michaelbel.movies.ui.compose.iconbutton.SettingsIcon
import org.michaelbel.movies.ui.icons.MoviesIcons
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeSmall
import org.michaelbel.movies.ui.ktx.modifierDisplayCutoutWindowInsets
import org.michaelbel.movies.ui.theme.MoviesTheme

@Composable
internal fun FeedToolbar(
    title: String,
    account: AccountPojo,
    isTmdbApiKeyEmpty: Boolean,
    onSearchIconClick: () -> Unit,
    onAuthIconClick: () -> Unit,
    onAccountIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge.copy(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            },
            modifier = modifier,
            navigationIcon = {
                SearchIcon(
                    onClick = onSearchIconClick,
                    modifier = Modifier.then(modifierDisplayCutoutWindowInsets)
                )
            },
            actions = {
                Row(
                    modifier = Modifier.then(modifierDisplayCutoutWindowInsets)
                ) {
                    SettingsIcon(
                        onClick = onSettingsIconClick
                    )

                    IconButton(
                        onClick = if (account.isEmpty) onAuthIconClick else onAccountIconClick
                    ) {
                        if (account.isEmpty) {
                            Image(
                                imageVector = MoviesIcons.AccountCircle,
                                contentDescription = stringResource(MoviesContentDescriptionCommon.AccountIcon),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                            )
                        } else {
                            AccountAvatar(
                                account = account,
                                fontSize = account.lettersTextFontSizeSmall,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                scrolledContainerColor = MaterialTheme.colorScheme.inversePrimary
            ),
            scrollBehavior = topAppBarScrollBehavior
        )

        if (isTmdbApiKeyEmpty) {
            ApiKeyBox(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
            )
        }
    }
}

@Composable
/*@DevicePreviews*/
private fun FeedToolbarPreview() {
    MoviesTheme {
        FeedToolbar(
            title = "Not Playing",
            account = AccountPojo.Empty,
            isTmdbApiKeyEmpty = true,
            onSearchIconClick = {},
            onAccountIconClick = {},
            onAuthIconClick = {},
            onSettingsIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}

@Composable
/*@Preview*/
private fun FeedToolbarAmoledPreview() {
    MoviesTheme(
        theme = AppTheme.Amoled
    ) {
        FeedToolbar(
            title = "Not Playing",
            account = AccountPojo.Empty,
            isTmdbApiKeyEmpty = true,
            onSearchIconClick = {},
            onAccountIconClick = {},
            onAuthIconClick = {},
            onSettingsIconClick = {},
            topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            modifier = Modifier.statusBarsPadding()
        )
    }
}