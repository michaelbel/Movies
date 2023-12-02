package org.michaelbel.movies.repository

import androidx.paging.PagingSource
import org.michaelbel.movies.network.Either
import org.michaelbel.movies.network.model.MovieResponse
import org.michaelbel.movies.network.model.Result
import org.michaelbel.movies.persistence.database.entity.MovieDb

interface MovieRepository {

    fun moviesPagingSource(pagingKey: String): PagingSource<Int, MovieDb>

    suspend fun moviesResult(movieList: String, page: Int): Result<MovieResponse>

    suspend fun movieDetails(movieId: Int): Either<MovieDb>

    suspend fun removeAllMovies(pagingKey: String)

    suspend fun insertAllMovies(pagingKey: String, movies: List<MovieResponse>)
}