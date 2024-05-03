package com.timeless.paybuddy.data.remote.firebase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.DocumentSnapshot
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PurchaseHistoryPagingSource @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : PagingSource<DocumentSnapshot, PurchaseHistory>(){

    override fun getRefreshKey(state: PagingState<DocumentSnapshot, PurchaseHistory>): DocumentSnapshot? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<DocumentSnapshot>): LoadResult<DocumentSnapshot, PurchaseHistory> {
        return try {
            val initialQuery = firebaseRepository.getUserPurchaseHistoryFromFirestore()!!
            val currentPage = initialQuery.get().await()
            val nextPage = initialQuery.startAfter(currentPage.documents.last()).get().await()

            val purchaseHistory = currentPage.toObjects(PurchaseHistory::class.java)

            LoadResult.Page(
                data = purchaseHistory,
                prevKey = null,
                nextKey = if (nextPage.isEmpty) null else nextPage.documents.last()
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }


}