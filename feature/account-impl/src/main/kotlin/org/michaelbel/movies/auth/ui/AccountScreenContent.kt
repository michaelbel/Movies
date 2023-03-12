package org.michaelbel.movies.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.michaelbel.movies.account_impl.R
import org.michaelbel.movies.auth.AccountViewModel
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.data.ktx.orEmpty
import org.michaelbel.movies.ui.compose.AccountAvatar
import org.michaelbel.movies.ui.ktx.lettersTextFontSizeLarge

@Composable
fun AccountRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val account: AccountDb? by viewModel.account.collectAsStateWithLifecycle()
    val loading: Boolean by viewModel.loading.collectAsStateWithLifecycle()

    AccountScreenContent(
        modifier = modifier,
        account = account.orEmpty,
        loading = loading,
        onBackClick = onBackClick,
        onLogoutClick = {
            viewModel.onLogoutClick {
                onBackClick()
            }
        }
    )
}

@Composable
internal fun AccountScreenContent(
    modifier: Modifier = Modifier,
    account: AccountDb,
    loading: Boolean,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    ConstraintLayout(
        modifier
            .padding(
                horizontal = 16.dp
            )
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.small
            )
    ) {
        val (
            toolbar,
            accountAvatar,
            nameColumn,
            nameText,
            usernameText,
            countryBox,
            logoutButton
        ) = createRefs()
        createVerticalChain(nameText, usernameText, chainStyle = ChainStyle.Packed)

        AccountToolbar(
            modifier = Modifier
                .constrainAs(toolbar) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            onNavigationIconClick = onBackClick
        )

        AccountAvatar(
            modifier = Modifier
                .constrainAs(accountAvatar) {
                    width = Dimension.value(64.dp)
                    height = Dimension.value(64.dp)
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(toolbar.bottom)
                },
            account = account,
            fontSize = account.lettersTextFontSizeLarge
        )

        Column(
            modifier = Modifier
                .constrainAs(nameColumn) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(accountAvatar.end, 12.dp)
                    top.linkTo(accountAvatar.top)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(accountAvatar.bottom)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            if (account.name.isNotEmpty()) {
                Text(
                    text = account.name,
                    color = MaterialTheme.colorScheme.primary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (account.username.isNotEmpty()) {
                Text(
                    text = account.username,
                    color = MaterialTheme.colorScheme.secondary,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (account.country.isNotEmpty()) {
            AccountCountryBox(
                modifier = Modifier
                    .constrainAs(countryBox) {
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                        start.linkTo(parent.start, 16.dp)
                        top.linkTo(accountAvatar.bottom, 8.dp)
                        end.linkTo(parent.end, 16.dp)
                    },
                country = account.country
            )
        }

        Button(
            onClick = onLogoutClick,
            modifier = Modifier
                .constrainAs(logoutButton) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(if (account.country.isNotEmpty()) countryBox.bottom else accountAvatar.bottom, 8.dp)
                    end.linkTo(parent.end, 16.dp)
                    bottom.linkTo(parent.bottom, 16.dp)
                },
            enabled = !loading,
            contentPadding = PaddingValues(
                horizontal = 24.dp
            )
        ) {
            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = stringResource(R.string.account_logout).uppercase(),
                )
            }
        }
    }
}