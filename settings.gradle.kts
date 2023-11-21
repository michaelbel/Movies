pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
    }
}

rootProject.name = "movies"

include(
    ":android-app",
    ":instant",
    ":benchmark",
    ":shared",

    ":core:analytics",
    ":core:common",
    ":core:interactor",
    ":core:interactor-impl",
    ":core:navigation",
    ":core:network",
    ":core:notifications",
    ":core:persistence",
    ":core:repository",
    ":core:repository-impl",
    ":core:ui",
    ":core:work",

    ":feature:account",
    ":feature:account-impl",
    ":feature:auth",
    ":feature:auth-impl",
    ":feature:details",
    ":feature:details-impl",
    ":feature:feed",
    ":feature:feed-impl",
    ":feature:gallery",
    ":feature:gallery-impl",
    ":feature:settings",
    ":feature:settings-impl"
)