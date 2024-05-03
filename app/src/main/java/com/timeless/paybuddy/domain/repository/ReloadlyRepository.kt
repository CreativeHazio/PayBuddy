package com.timeless.paybuddy.domain.repository

import com.timeless.paybuddy.domain.model.PurchaseHistory
import retrofit2.Response

interface ReloadlyRepository {

    suspend fun buyData(
        amount : Int,
        accountReference : String,
        purchaseHistory : PurchaseHistory
    ) : Response<String>

}