package org.michaelbel.movies.settings.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.common.biometric.di.biometricKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.networkManagerKoinModule
import org.michaelbel.movies.settings.SettingsViewModel

actual val settingsKoinModule = module {
    includes(
        biometricKoinModule,
        interactorKoinModule,
        networkManagerKoinModule
    )
    viewModel { SettingsViewModel(get(), get(), get(), get(), get()) }
}