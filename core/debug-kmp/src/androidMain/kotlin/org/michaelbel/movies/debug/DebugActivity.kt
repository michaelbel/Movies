package org.michaelbel.movies.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.movies.debug.ui.DebugActivityContent

@AndroidEntryPoint
internal class DebugActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DebugActivityContent { statusBarStyle, navigationBarStyle ->
                enableEdgeToEdge(statusBarStyle, navigationBarStyle)
            }
        }
    }
}