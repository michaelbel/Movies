plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.google.ksp)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }
    }
    jvm("desktop")

    sourceSets {
        commonMain.dependencies {
            implementation(project(":core:common-kmp"))
            implementation(libs.bundles.androidx.datastore.common)
            implementation(libs.bundles.koin.common)
        }
        androidMain.dependencies {
            implementation(project(":core:network-kmp"))
            implementation(libs.bundles.androidx.datastore.android)
            api(libs.bundles.androidx.room)
            implementation(libs.koin.android)
        }
        val desktopMain by getting
        desktopMain.dependencies {
            implementation(libs.bundles.androidx.datastore.desktop)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.persistence_kmp"

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }

    dependencies {
        ksp(libs.androidx.room.compiler)
    }
}