package org.michaelbel.data.remote.model

import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar") val gravatar: GrAvatar
)