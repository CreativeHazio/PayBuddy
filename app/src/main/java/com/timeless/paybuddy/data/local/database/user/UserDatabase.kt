package com.timeless.paybuddy.data.local.database.user

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timeless.paybuddy.data.local.typeConverter.PurchaseHistoryConverters
import com.timeless.paybuddy.data.local.typeConverter.WalletConverters
import com.timeless.paybuddy.data.local.database.user.model.PurchaseHistoryEntity
import com.timeless.paybuddy.data.local.database.user.model.UserBalanceEntity
import com.timeless.paybuddy.data.local.database.user.repository.UserDAO
import com.timeless.paybuddy.data.local.database.user.model.UserEntity

//TODO: Change kapt to ksp for room dependency
@Database(
    version = 2,
    entities = [UserEntity::class, UserBalanceEntity::class, PurchaseHistoryEntity::class],
    exportSchema = true
)
@TypeConverters(WalletConverters::class, PurchaseHistoryConverters::class)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDao : UserDAO

}