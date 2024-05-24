package com.timeless.paybuddy.data.local.database.user.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_balance_table")
data class UserBalanceEntity(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val amount : Double
)
