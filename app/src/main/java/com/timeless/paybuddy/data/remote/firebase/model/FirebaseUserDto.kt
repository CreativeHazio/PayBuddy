package com.timeless.paybuddy.data.remote.firebase.model

import com.google.gson.annotations.SerializedName

data class FirebaseUserDto (
    @SerializedName("user_id")
    var userId: String? = "",
    var email: String? = "",
    var username: String? = "",
    @SerializedName("mobile_number")
    var mobileNumber: String? = "",
    var provider: String? = "",
    var wallet: FirebaseWalletDto? = null
)