package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("iso_639_1") val language: String,
    @SerializedName("name") val name: String
)