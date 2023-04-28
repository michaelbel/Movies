@Suppress("dsl_scope_violation")
plugins {
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.test) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.firebase.appdistribution) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.androidx.navigation.safeargs) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
    alias(libs.plugins.palantir.git)
    alias(libs.plugins.ben.manes.versions)
    alias(libs.plugins.littlerobots.version.catalog.update)
}

/**
 * https://github.com/littlerobots/version-catalog-update-plugin
 * ./gradlew versionCatalogUpdate
 */
versionCatalogUpdate {
    sortByKey.set(false)
    keep {
        keepUnusedVersions.set(true)
        keepUnusedLibraries.set(true)
        keepUnusedPlugins.set(true)
    }
}