package org.michaelbel.movies.network.config

import org.michaelbel.movies.network.model.image.BackdropSize
import org.michaelbel.movies.network.model.image.PosterSize
import org.michaelbel.movies.network.model.image.ProfileSize

/**
 * See [TMDB Image Basics](https://developer.themoviedb.org/docs/image-basics)
 *
 * See [TMDB Images Configuration](https://developer.themoviedb.org/reference/configuration-details)
 */
private const val TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/%s%s"
private const val IMAGE_EMPTY_URL = "https://null"

val String.formatPosterImage: String
    get() = String.format(TMDB_IMAGE_BASE_URL, PosterSize.W780.size, this).ifEmpty { IMAGE_EMPTY_URL }

val String.formatBackdropImage: String
    get() = String.format(TMDB_IMAGE_BASE_URL, BackdropSize.W1280.size, this).ifEmpty { IMAGE_EMPTY_URL }

val String.formatProfileImage: String
    get() = String.format(TMDB_IMAGE_BASE_URL, ProfileSize.W185.size, this).ifEmpty { IMAGE_EMPTY_URL }

@Suppress("unused")
val String.original: String
    get() {
        return when {
            this.isNotEmpty() -> {
                val size: String = substring(indexOf("/p").plus(3), lastIndexOf("/"))
                replace(size, BackdropSize.ORIGINAL.size)
            }
            else -> this
        }
    }

val String.isNotOriginal: Boolean
    get() = !contains("original".toRegex())

fun String.formatImage(size: String): String {
    return String.format(TMDB_IMAGE_BASE_URL, size, this).ifEmpty { IMAGE_EMPTY_URL }
}