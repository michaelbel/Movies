import org.michaelbel.moviemade.App.namespace
import org.michaelbel.moviemade.dependencies.OptExperimentalCoroutinesApi
import org.michaelbel.moviemade.dependencies.OptExperimentalLifecycleComposeApi
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterial3Api
import org.michaelbel.moviemade.dependencies.OptExperimentalMaterialApi

plugins {
    id("movies-android-library")
    id("movies-android-library-compose")
    id("movies-android-hilt")
}

android {
    namespace = namespace("settings")

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterialApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalMaterial3Api
        freeCompilerArgs = freeCompilerArgs + OptExperimentalCoroutinesApi
        freeCompilerArgs = freeCompilerArgs + OptExperimentalLifecycleComposeApi
    }
}

dependencies {
    implementation(project(":core:ads"))
    implementation(project(":core:analytics"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:ui"))
}