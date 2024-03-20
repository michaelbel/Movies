package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorMovieService
import org.michaelbel.movies.network.model.ImagesResponse
import org.michaelbel.movies.network.model.Movie
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.network.retrofit.RetrofitMovieService
import javax.inject.Inject

/**
 * You can replace [ktorMovieService] with [retrofitMovieService] to use it.
 */
class MovieNetworkService @Inject internal constructor(
    private val retrofitMovieService: RetrofitMovieService,
    private val ktorMovieService: KtorMovieService
) {

    suspend fun movies(
        list: String,
        language: String,
        page: Int
    ): Result<MovieResponse> {
        return ktorMovieService.movies(list, language, page)
    }

    suspend fun movie(
        movieId: Int,
        language: String
    ): Movie {
        return ktorMovieService.movie(movieId, language)
    }

    suspend fun images(
        movieId: Int
    ): ImagesResponse {
        return ktorMovieService.images(movieId)
    }
}