package com.timeless.paybuddy.domain.usecase.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.timeless.paybuddy.data.remote.firebase.PurchaseHistoryPagingSource
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.util.Constants
import kotlinx.coroutines.flow.Flow

class GetUserPurchaseHistory(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke() : Flow<PagingData<PurchaseHistory>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.FIRESTORE_PAGE_SIZE
            )
        ) {
            PurchaseHistoryPagingSource(firebaseRepository)
        }.flow
    }

}