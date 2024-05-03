package com.timeless.paybuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String,
    val email: String,
    val username: String,
    val mobileNumber: String,
    val provider: String,
    val wallet: Wallet?
) : Parcelable

// TODO: Add user to Local DB so that when Firestore add fails you will be able to add the details later
