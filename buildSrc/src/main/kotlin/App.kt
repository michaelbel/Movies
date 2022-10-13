package org.michaelbel.moviemade

object App {
    const val VersionName = "1.3.9"

    internal const val MinSdk = 21
    internal const val TargetSdk = 33
    internal const val CompileSdk = 33
    const val BuildTools = "33.0.0"

    const val ApplicationId = "org.michaelbel.moviemade"

    const val TestInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    fun namespace(module: String): String {
        return "org.michaelbel.movies.$module"
    }
}