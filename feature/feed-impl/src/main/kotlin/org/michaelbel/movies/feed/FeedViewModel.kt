package org.michaelbel.movies.feed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.data.AppDatabase
import org.michaelbel.movies.domain.data.entity.MovieDb
import org.michaelbel.movies.domain.interactor.MovieInteractor
import org.michaelbel.movies.domain.interactor.SettingsInteractor
import org.michaelbel.movies.domain.mediator.MoviesRemoteMediator
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.connectivity.NetworkManager
import org.michaelbel.movies.network.connectivity.NetworkStatus
import org.michaelbel.movies.network.model.MovieResponse

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor,
    appDatabase: AppDatabase,
    settingsInteractor: SettingsInteractor,
    networkManager: NetworkManager
): BaseViewModel() {

    private val moviesList: String
        get() = if (isTmdbApiKeyEmpty) MovieDb.MOVIES_LOCAL_LIST else MovieResponse.NOW_PLAYING

    val pagingItems: Flow<PagingData<MovieDb>> = Pager(
        config = PagingConfig(
            pageSize = MovieResponse.DEFAULT_PAGE_SIZE
        ),
        remoteMediator = MoviesRemoteMediator(
            appDatabase = appDatabase,
            movieInteractor = movieInteractor,
            movieList = MovieResponse.NOW_PLAYING
        ),
        pagingSourceFactory = { movieInteractor.moviesPagingSource(moviesList) }
    ).flow
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )
        .cachedIn(this)

    val networkStatus: StateFlow<NetworkStatus> = networkManager.status
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = NetworkStatus.Unavailable
        )

    val isSettingsIconVisible: StateFlow<Boolean> = settingsInteractor.isSettingsIconVisible
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = true
        )
}