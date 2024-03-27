package org.michaelbel.movies.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Network(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("homepage") val homepage: String,
    @SerialName("headquarters") val headquarters: String,
    @SerialName("origin_country") val originCountry: String
)