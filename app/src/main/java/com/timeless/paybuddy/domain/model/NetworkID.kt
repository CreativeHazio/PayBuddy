package com.timeless.paybuddy.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetworkID(
    val name : String,
    val image : Int,
    val imageColored : Int,
) : Parcelable
