plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
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
            implementation(compose.material3)
            implementation(libs.bundles.kotlinx.coroutines.common)
            implementation(libs.bundles.paging.common)
            implementation(libs.bundles.koin.common)
            api(libs.bundles.kmp.viewmodel.common)
        }
        androidMain.dependencies {
            api(libs.bundles.kotlinx.coroutines.android)
            api(libs.bundles.lifecycle.android)
            api(libs.bundles.activity.android)
            api(libs.bundles.biometric.android)
            api(libs.bundles.core.android)
            api(libs.bundles.startup.android)
            api(libs.bundles.work.android)
            api(libs.bundles.timber.android)
            implementation(libs.bundles.appcompat.android)
            implementation(libs.bundles.browser.android)
            implementation(libs.bundles.koin.android)
        }
    }
}

android {
    namespace = "org.michaelbel.movies.common_kmp"
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.min.sdk.get().toInt()
        compileSdk = libs.versions.compile.sdk.get().toInt()
    }

    buildFeatures {
        buildConfig = true
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