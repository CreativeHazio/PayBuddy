package com.timeless.paybuddy.data.remote.firebase

import com.timeless.paybuddy.domain.model.Wallet

data class FirebaseUserDto (
    var userId: String? = "",
    var email: String? = "",
    var username: String? = "",
    var mobileNumber: String? = "",
    var provider: String? = "",
    var wallet: FirebaseWalletDto? = null
)