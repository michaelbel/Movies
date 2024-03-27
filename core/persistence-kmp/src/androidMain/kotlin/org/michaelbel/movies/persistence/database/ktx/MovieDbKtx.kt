package org.michaelbel.movies.persistence.database.ktx

import org.michaelbel.movies.network.config.TMDB_MOVIE_URL
import org.michaelbel.movies.persistence.database.entity.MovieDb
import java.util.Locale

val MovieDb.isNotEmpty: Boolean
    get() = this != MovieDb.Empty

val MovieDb.url: String
    get() = String.format(Locale.US, TMDB_MOVIE_URL, movieId)

val MovieDb?.orEmpty: MovieDb
    get() = this ?: MovieDb.Empty