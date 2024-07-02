package com.timeless.paybuddy.data.remote.flutterwave.model

import com.google.gson.annotations.SerializedName

data class FlutterwaveUserDto(
    @SerializedName("user_id")
    val userId : String,
    @SerializedName("account_name")
    val accountName : String,
    val email : String,
    @SerializedName("mobile_number")
    val mobileNumber : String,
    val country : String
)