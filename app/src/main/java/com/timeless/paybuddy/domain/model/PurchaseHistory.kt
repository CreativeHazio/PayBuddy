package com.timeless.paybuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PurchaseHistory(
    val transactionID : String,
    val networkId : String,
    val plan : String,
    val amount : String,
    val date : String,
    val mobileNumber: String,
    val status : String
) : Parcelable
