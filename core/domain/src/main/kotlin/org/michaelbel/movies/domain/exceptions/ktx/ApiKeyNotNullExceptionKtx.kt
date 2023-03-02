package org.michaelbel.movies.domain.exceptions.ktx

import org.michaelbel.movies.domain.exceptions.ApiKeyNotNullException
import org.michaelbel.movies.entities.isTmdbApiKeyEmpty

internal fun checkApiKeyNotNullException() {
    if (isTmdbApiKeyEmpty) throw ApiKeyNotNullException
}