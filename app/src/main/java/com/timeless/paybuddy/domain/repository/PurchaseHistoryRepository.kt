package com.timeless.paybuddy.domain.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.timeless.paybuddy.data.local.database.user.model.PurchaseHistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface PurchaseHistoryRepository {

    suspend fun updatePurchaseHistory(purchaseHistory: List<PurchaseHistoryEntity>)

    fun getPurchaseHistoryPagingSource(): PagingSource<Int, PurchaseHistoryEntity>

    fun getPurchaseHistory(scope: CoroutineScope): Flow<PagingData<PurchaseHistoryEntity>>

    suspend fun deletePurchaseHistory()
}