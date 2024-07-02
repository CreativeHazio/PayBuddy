package com.timeless.paybuddy.domain.usecase.user

import androidx.paging.PagingData
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow

class GetUserPurchaseHistory(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke() : Flow<PagingData<PurchaseHistory>> {
        return firebaseRepository.getUserPurchaseHistoryPagingData()
    }

}