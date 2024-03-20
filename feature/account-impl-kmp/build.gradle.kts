plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    id("movies-android-hilt")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    sourceSets {
        androidMain.dependencies {
            api(project(":core:navigation-kmp"))
            api(project(":core:ui"))
            implementation(project(":core:ui-kmp"))
            implementation(project(":core:common-kmp"))
            implementation(project(":core:interactor-kmp"))
            implementation(project(":core:network"))
            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.androidx.hilt.work)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.account_impl_kmp"

    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    lint {
        quiet = true
        abortOnError = false
        ignoreWarnings = true
        checkDependencies = true
        lintConfig = file("${project.rootDir}/config/codestyle/lint.xml")
    }
}