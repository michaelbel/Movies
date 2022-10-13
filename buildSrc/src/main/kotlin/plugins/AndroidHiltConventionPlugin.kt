package org.michaelbel.moviemade.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.michaelbel.moviemade.dependencies.implementationHiltDependencies

class AndroidHiltConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
            }
            dependencies {
                implementationHiltDependencies()
            }
        }
    }
}