package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("iso_3166_1") val country: String,
    @SerializedName("name") val name: String
)