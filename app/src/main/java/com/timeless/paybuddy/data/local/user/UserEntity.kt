package com.timeless.paybuddy.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timeless.paybuddy.domain.model.Wallet

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val userId: String,
    val email: String,
    val username: String,
    val mobileNumber: String,
    val provider: String,
    val wallet: Wallet?
)
