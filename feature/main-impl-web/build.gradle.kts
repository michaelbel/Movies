@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    js {
        browser {}
    }
    wasmJs()

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.feedWeb)
            api(libs.bundles.jetbrains.androidx.navigation.compose.common)
            api(libs.bundles.koin.common)
            implementation(compose.material3)
        }
    }

    compilerOptions {
        jvmToolchain(libs.versions.jdk.get().toInt())
    }
}