package com.timeless.paybuddy.domain.usecase.data

import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.ReloadlyRepository
import retrofit2.Response

class BuyData(
    private val reloadlyRepository: ReloadlyRepository
) {

    suspend operator fun invoke(
        amount : Int,
        accountReference : String,
        purchaseHistory: PurchaseHistory
    ): Response<String> {
        return reloadlyRepository.buyData(amount, accountReference, purchaseHistory)
    }

}