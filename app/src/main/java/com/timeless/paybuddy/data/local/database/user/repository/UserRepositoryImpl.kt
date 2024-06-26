package com.timeless.paybuddy.data.local.database.user.repository

import com.timeless.paybuddy.data.local.database.user.model.UserBalanceEntity
import com.timeless.paybuddy.data.local.database.user.model.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDAO: UserDAO
) {

    suspend fun updateUser(userEntity: UserEntity) {
        userDAO.updateUser(userEntity)
    }

    suspend fun updateBalance(userBalanceEntity: UserBalanceEntity) {
        userDAO.updateBalance(userBalanceEntity)
    }

    fun getUser(): Flow<UserEntity> {
        return userDAO.getUser()
    }

    fun getBalance(): Flow<UserBalanceEntity> {
        return userDAO.getBalance()
    }

    suspend fun deleteUser() {
        userDAO.deleteUser()
    }

    suspend fun deleteBalance() {
        userDAO.deleteBalance()
    }
}