package org.michaelbel.movies.repository.impl

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import org.michaelbel.movies.common.exceptions.MovieDetailsException
import org.michaelbel.movies.common.exceptions.MoviesUpcomingException
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.network.MovieNetworkService
import org.michaelbel.movies.network.config.isTmdbApiKeyEmpty
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.MoviePersistence
import org.michaelbel.movies.persistence.database.entity.mini.MovieDbMini
import org.michaelbel.movies.persistence.database.entity.pojo.MoviePojo
import org.michaelbel.movies.persistence.database.ktx.moviePojo
import org.michaelbel.movies.persistence.database.ktx.orEmpty
import org.michaelbel.movies.persistence.database.typealiases.Limit
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.persistence.database.typealiases.Page
import org.michaelbel.movies.persistence.database.typealiases.PagingKey
import org.michaelbel.movies.repository.MovieRepository
import org.michaelbel.movies.repository.ktx.checkApiKeyNotNullException

internal class MovieRepositoryImpl(
    private val movieNetworkService: MovieNetworkService,
    private val moviePersistence: MoviePersistence
): MovieRepository {

    override fun moviesPagingSource(
        pagingKey: PagingKey
    ): PagingSource<Int, MoviePojo> {
        return moviePersistence.pagingSource(pagingKey)
    }

    override fun moviesFlow(
        pagingKey: PagingKey,
        limit: Limit
    ): Flow<List<MoviePojo>> {
        return moviePersistence.moviesFlow(pagingKey, limit)
    }

    override suspend fun moviesResult(
        pagingKey: PagingKey,
        language: String,
        page: Page
    ): Result<MovieResponse> {
        if (isTmdbApiKeyEmpty && moviePersistence.isEmpty(MoviePojo.MOVIES_LOCAL_LIST)) {
            checkApiKeyNotNullException()
        }

        return movieNetworkService.movies(
            list = pagingKey,
            language = language,
            page = page
        )
    }

    override suspend fun movie(
        pagingKey: PagingKey,
        movieId: MovieId
    ): MoviePojo {
        return moviePersistence.movieById(pagingKey, movieId).orEmpty
    }

    override suspend fun movieDetails(
        pagingKey: PagingKey,
        language: String,
        movieId: MovieId
    ): MoviePojo {
        return try {
            moviePersistence.movieById(pagingKey, movieId) ?: movieNetworkService.movie(movieId, language).moviePojo
        } catch (ignored: Exception) {
            throw MovieDetailsException
        }
    }

    override suspend fun moviesWidget(
        language: String
    ): List<MovieDbMini> {
        return try {
            val movieResult = movieNetworkService.movies(
                list = MovieList.Upcoming().name,
                language = language,
                page = 1
            )
            val moviesDb = movieResult.results.mapIndexed { index, movieResponse ->
                movieResponse.moviePojo(
                    movieList = MoviePojo.MOVIES_WIDGET,
                    position = index.plus(1)
                )
            }
            moviePersistence.removeMovies(MoviePojo.MOVIES_WIDGET)
            moviePersistence.insertMovies(moviesDb)
            moviePersistence.moviesMini(MoviePojo.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE)
        } catch (ignored: Exception) {
            moviePersistence.moviesMini(MoviePojo.MOVIES_WIDGET, MovieResponse.DEFAULT_PAGE_SIZE).ifEmpty {
                throw MoviesUpcomingException
            }
        }
    }

    override suspend fun removeMovies(pagingKey: PagingKey) {
        moviePersistence.removeMovies(pagingKey)
    }

    override suspend fun removeMovie(pagingKey: PagingKey, movieId: MovieId) {
        moviePersistence.removeMovie(pagingKey, movieId)
    }

    override suspend fun insertMovies(
        pagingKey: PagingKey,
        page: Page,
        movies: List<MovieResponse>
    ) {
        val maxPosition = moviePersistence.maxPosition(pagingKey).orEmpty()
        val moviesDb = movies.mapIndexed { index, movieResponse ->
            movieResponse.moviePojo(
                movieList = pagingKey,
                page = page,
                position = if (maxPosition == 0) index else maxPosition.plus(index).plus(1)
            )
        }
        moviePersistence.insertMovies(moviesDb)
    }

    override suspend fun insertMovie(pagingKey: PagingKey, movie: MoviePojo) {
        val maxPosition = moviePersistence.maxPosition(pagingKey).orEmpty()
        moviePersistence.insertMovie(
            movie.copy(
                movieList = pagingKey,
                dateAdded = Clock.System.now().toEpochMilliseconds(),
                position = maxPosition.plus(1)
            )
        )
    }

    override suspend fun updateMovieColors(
        movieId: MovieId,
        containerColor: Int,
        onContainerColor: Int
    ) {
        moviePersistence.updateMovieColors(movieId, containerColor, onContainerColor)
    }
}