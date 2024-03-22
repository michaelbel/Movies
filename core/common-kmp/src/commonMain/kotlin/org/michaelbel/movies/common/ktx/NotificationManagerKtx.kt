@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.common.ktx

import android.content.Context
import androidx.core.app.NotificationManagerCompat

expect val Context.notificationManager: NotificationManagerCompat