package org.michaelbel.movies.interactor.impl

import kotlinx.coroutines.withContext
import org.michaelbel.movies.common.dispatchers.MoviesDispatchers
import org.michaelbel.movies.interactor.NotificationInteractor
import org.michaelbel.movies.repository.NotificationRepository

internal class NotificationInteractorImpl(
    private val dispatchers: MoviesDispatchers,
    private val notificationRepository: NotificationRepository
): NotificationInteractor {

    override suspend fun notificationExpireTime(): Long {
        return withContext(dispatchers.io) { notificationRepository.notificationExpireTime() }
    }

    override suspend fun updateNotificationExpireTime() {
        withContext(dispatchers.io) { notificationRepository.updateNotificationExpireTime() }
    }
}