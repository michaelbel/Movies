package org.michaelbel.movies.persistence.database

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.persistence.database.dao.AccountDao
import org.michaelbel.movies.persistence.database.entity.AccountDb

class AccountPersistence internal constructor(
    private val accountDao: AccountDao
) {

    fun accountById(accountId: Int): Flow<AccountDb?> {
        return accountDao.accountById(accountId)
    }

    suspend fun insert(account: AccountDb) {
        accountDao.insert(account)
    }

    suspend fun removeById(accountId: Int) {
        accountDao.removeById(accountId)
    }
}