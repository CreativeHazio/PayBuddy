package com.timeless.paybuddy.data.local.user

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {

    @Upsert
    fun updateUser(userEntity: UserEntity)

    @Upsert
    fun updateBalance(userBalanceEntity: UserBalanceEntity)

    @Query("SELECT * FROM user_table")
    fun getUser() : Flow<UserEntity>

    @Query("SELECT * FROM user_balance_table")
    fun getBalance() : Flow<UserBalanceEntity>

    @Query("SELECT * FROM purchase_history_table")
    fun getUserPurchaseHistory() : Flow<PurchaseHistoryEntity>

    @Query("DELETE FROM user_table")
    fun deleteUser() : Int

    @Query("DELETE FROM user_balance_table")
    fun deleteBalance() : Int

}