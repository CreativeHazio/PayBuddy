package com.timeless.paybuddy.data.remote.firebase.model

data class FirebaseUserDto (
    var userId: String? = "",
    var email: String? = "",
    var username: String? = "",
    var mobileNumber: String? = "",
    var provider: String? = "",
    var wallet: FirebaseWalletDto? = null
)