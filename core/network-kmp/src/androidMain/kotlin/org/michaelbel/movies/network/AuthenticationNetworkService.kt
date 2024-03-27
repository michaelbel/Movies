@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package org.michaelbel.movies.network

import org.michaelbel.movies.network.ktor.KtorAuthenticationService
import org.michaelbel.movies.network.model.DeletedSession
import org.michaelbel.movies.network.model.RequestToken
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.SessionRequest
import org.michaelbel.movies.network.model.Token
import org.michaelbel.movies.network.model.Username
import org.michaelbel.movies.network.retrofit.RetrofitAuthenticationService

/**
 * You can replace [ktorAuthenticationService] with [retrofitAuthenticationService] to use it.
 */
actual class AuthenticationNetworkService internal constructor(
    private val retrofitAuthenticationService: RetrofitAuthenticationService,
    private val ktorAuthenticationService: KtorAuthenticationService
) {

    suspend fun createRequestToken(): Token {
        return ktorAuthenticationService.createRequestToken()
    }

    suspend fun createSessionWithLogin(
        username: Username
    ): Token {
        return ktorAuthenticationService.createSessionWithLogin(username)
    }

    suspend fun createSession(
        authToken: RequestToken
    ): Session {
        return ktorAuthenticationService.createSession(authToken)
    }

    suspend fun deleteSession(
        sessionRequest: SessionRequest
    ): DeletedSession {
        return ktorAuthenticationService.deleteSession(sessionRequest)
    }
}