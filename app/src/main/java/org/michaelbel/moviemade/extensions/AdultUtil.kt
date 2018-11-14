package org.michaelbel.moviemade.extensions

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object AdultUtil {

    private const val SP_NAME = "mainconfig"
    private const val KEY_ADULT = "adult"

    fun includeAdult(context: Context): Boolean {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(SP_NAME, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_ADULT, true)
    }
}