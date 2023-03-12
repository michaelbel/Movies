package org.michaelbel.movies.domain.repository.authentication

import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token

interface AuthenticationRepository {

    suspend fun createRequestToken(): Token

    suspend fun createSessionWithLogin(
        username: String,
        password: String,
        requestToken: String
    ): Token

    suspend fun createSession(token: String): Session

    suspend fun deleteSession()
}