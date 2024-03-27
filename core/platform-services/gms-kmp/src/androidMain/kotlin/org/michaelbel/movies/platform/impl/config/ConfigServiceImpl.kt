package org.michaelbel.movies.platform.impl.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.michaelbel.movies.platform.config.ConfigService

class ConfigServiceImpl(
    private val firebaseRemoteConfig: FirebaseRemoteConfig
): ConfigService {

    override fun fetchAndActivate() {
        firebaseRemoteConfig.fetchAndActivate()
    }

    override fun getBooleanFlow(name: String): Flow<Boolean> {
        return flowOf(firebaseRemoteConfig.getBoolean(name))
    }
}