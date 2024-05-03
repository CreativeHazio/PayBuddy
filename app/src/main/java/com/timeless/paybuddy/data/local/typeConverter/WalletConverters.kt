package com.timeless.paybuddy.data.local.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.timeless.paybuddy.domain.model.Wallet

class WalletConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromWallet(wallet: Wallet): String? {
        return gson.toJson(wallet)
    }

    @TypeConverter
    fun toWallet(walletString: String) : Wallet {
        return gson.fromJson(walletString, Wallet::class.java)
    }

}