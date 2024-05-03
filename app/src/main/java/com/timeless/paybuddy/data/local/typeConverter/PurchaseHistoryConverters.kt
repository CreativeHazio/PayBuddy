package com.timeless.paybuddy.data.local.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.timeless.paybuddy.domain.model.PurchaseHistory

class PurchaseHistoryConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromPurchaseHistory(purchaseHistory: PurchaseHistory) : String {
        return gson.toJson(purchaseHistory)
    }

    @TypeConverter
    fun toPurchaseHistory(purchaseHistoryString: String) : PurchaseHistory {
        return gson.fromJson(purchaseHistoryString, PurchaseHistory::class.java)
    }
}