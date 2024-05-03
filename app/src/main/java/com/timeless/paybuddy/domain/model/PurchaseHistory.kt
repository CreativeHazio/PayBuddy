package com.timeless.paybuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseHistory(
    val transactionID : String,
    val networkID : String,
    val plan : String,
    val amount : String,
    val date : String,
    val phoneNumber: String,
    val status : String
) : Parcelable
