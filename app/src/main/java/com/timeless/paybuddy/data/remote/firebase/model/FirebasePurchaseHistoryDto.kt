package com.timeless.paybuddy.data.remote.firebase.model

data class FirebasePurchaseHistoryDto(
    val transactionID : String? = null,
    val networkID : String? = null,
    val plan : String? = null,
    val amount : String? = null,
    val date : String? = null,
    val phoneNumber: String? = null,
    val status : String? = null
)
