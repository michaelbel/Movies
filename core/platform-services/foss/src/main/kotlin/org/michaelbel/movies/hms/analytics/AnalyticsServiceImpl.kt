package org.michaelbel.movies.hms.analytics

import android.os.Bundle
import javax.inject.Inject
import org.michaelbel.movies.platform.main.analytics.AnalyticsService

internal class AnalyticsServiceImpl @Inject constructor(): AnalyticsService {

    override val screenView: String = ""

    override val screenName: String = ""

    override fun logEvent(name: String, params: Bundle) {}
}