package org.michaelbel.moviemade.plugins

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.michaelbel.moviemade.ktx.configureKotlinAndroid
import org.michaelbel.moviemade.App.TargetSdk

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = TargetSdk
            }
        }
    }
}