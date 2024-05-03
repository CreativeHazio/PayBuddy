package com.timeless.paybuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet(
    val accountName: String,
    val accountReference: String,
    val bankName: String,
    val nuban: String,
    val status: String
) : Parcelable