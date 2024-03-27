package org.michaelbel.movies.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.details.DetailsViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.network.connectivity.di.networkManagerKoinModule

val detailsKoinModule = module {
    includes(
        interactorKoinModule,
        networkManagerKoinModule
    )
    viewModel { DetailsViewModel(get(), get(), get()) }
}