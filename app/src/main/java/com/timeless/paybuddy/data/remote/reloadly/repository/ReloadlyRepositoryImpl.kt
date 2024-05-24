package com.timeless.paybuddy.data.remote.reloadly.repository

import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.ReloadlyRepository
import retrofit2.Response
import javax.inject.Inject

class ReloadlyRepositoryImpl @Inject constructor(
    private val reloadlyService: ReloadlyService
) : ReloadlyRepository {

    override suspend fun buyData(
        amount : Int,
        accountReference : String,
        purchaseHistory : PurchaseHistory
    ) : Response<String> {
        return reloadlyService.buyData(
            amount,
            accountReference,
            purchaseHistory
        )
    }

}