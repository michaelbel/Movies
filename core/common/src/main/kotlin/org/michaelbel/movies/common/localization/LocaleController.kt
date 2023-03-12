package org.michaelbel.movies.common.localization

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.localization.model.AppLanguage

interface LocaleController {

    val language: String

    val appLanguage: Flow<AppLanguage>

    suspend fun selectLanguage(language: AppLanguage)
}