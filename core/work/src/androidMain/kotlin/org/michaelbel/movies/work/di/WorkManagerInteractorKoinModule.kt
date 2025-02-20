package org.michaelbel.movies.work.di

import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.michaelbel.movies.common.dispatchers.di.dispatchersKoinModule
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.notifications.di.notificationClientKoinModule
import org.michaelbel.movies.persistence.database.di.persistenceKoinModule
import org.michaelbel.movies.work.AccountUpdateWorker
import org.michaelbel.movies.work.DownloadImageWorker
import org.michaelbel.movies.work.MoviesDatabaseWorker
import org.michaelbel.movies.work.WorkManagerInteractor
import org.michaelbel.movies.work.impl.WorkManagerInteractorImpl

actual val workManagerInteractorKoinModule = module {
    includes(
        interactorKoinModule,
        dispatchersKoinModule,
        persistenceKoinModule,
        notificationClientKoinModule
    )
    single { WorkManager.getInstance(androidContext()) }
    workerOf(::AccountUpdateWorker)
    workerOf(::DownloadImageWorker)
    workerOf(::MoviesDatabaseWorker)
    singleOf(::WorkManagerInteractorImpl) { bind<WorkManagerInteractor>() }
}