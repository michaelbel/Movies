package org.michaelbel.movies.domain.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.michaelbel.movies.domain.repository.account.AccountRepository
import org.michaelbel.movies.domain.repository.account.impl.AccountRepositoryImpl
import org.michaelbel.movies.domain.repository.authentication.AuthenticationRepository
import org.michaelbel.movies.domain.repository.authentication.impl.AuthenticationRepositoryImpl
import org.michaelbel.movies.domain.repository.movie.MovieRepository
import org.michaelbel.movies.domain.repository.movie.impl.MovieRepositoryImpl
import org.michaelbel.movies.domain.repository.settings.SettingsRepository
import org.michaelbel.movies.domain.repository.settings.impl.SettingsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideMovieRepository(
        repository: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    fun provideAuthenticationRepository(
        repository: AuthenticationRepositoryImpl
    ): AuthenticationRepository

    @Binds
    @Singleton
    fun provideAccountRepository(
        repository: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    @Singleton
    fun provideSettingsRepository(
        repository: SettingsRepositoryImpl
    ): SettingsRepository
}