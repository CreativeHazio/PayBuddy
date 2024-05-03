package com.timeless.paybuddy.data.local.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "purchase_history_table")
data class PurchaseHistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val transactionID : String,
    val networkID : String,
    val plan : String,
    val amount : String,
    val date : String,
    val phoneNumber: String,
    val status : String
)
