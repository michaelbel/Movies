package org.michaelbel.movies.domain.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "accounts")
data class AccountDb(
    @NotNull @PrimaryKey @ColumnInfo(name = "id") val accountId: Int,
    @NotNull val avatarUrl: String,
    @NotNull val language: String,
    @NotNull val country: String,
    @NotNull val name: String,
    @NotNull val adult: Boolean,
    @NotNull val username: String
) {
    companion object {
        val Empty: AccountDb = AccountDb(
            accountId = 0,
            avatarUrl = "",
            language = "",
            country = "",
            name = "",
            adult = false,
            username = ""
        )
    }
}