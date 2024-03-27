package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Country(
    @SerialName("iso_3166_1") val country: String,
    @SerialName("name") val name: String
)