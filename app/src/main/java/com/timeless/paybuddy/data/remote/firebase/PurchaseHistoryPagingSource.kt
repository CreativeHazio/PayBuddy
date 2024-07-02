package com.timeless.paybuddy.data.remote.firebase

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.timeless.paybuddy.data.mapper.PurchaseHistoryMapper
import com.timeless.paybuddy.data.remote.firebase.model.FirebasePurchaseHistoryDto
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.util.Constants
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PurchaseHistoryPagingSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : PagingSource<QuerySnapshot, PurchaseHistory>(){

    override fun getRefreshKey(state: PagingState<QuerySnapshot, PurchaseHistory>): QuerySnapshot {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, PurchaseHistory> {
        return try {
            val user = auth.currentUser
            val currentPage = params.key ?: user?.run {
                firestore.collection(Constants.FIREBASE_PURCHASE_HISTORY_COLLECTION)
                    // User history is stored with random id, but also user uid is used to know which user has it
                    .whereEqualTo(Constants.FIREBASE_USERS_USERID, user.uid)
                    .orderBy("date", Query.Direction.DESCENDING)
                    .limit(Constants.FIRESTORE_PAGE_SIZE.toLong())
                    .get()
                    .await()
            }
            println("Current page: $currentPage")
            val lastVisible = currentPage!!.documents.lastOrNull()
            println("Last Visible: $lastVisible")

            val purchaseHistoryDto = currentPage.toObjects(FirebasePurchaseHistoryDto::class.java)
            val purchaseHistory = PurchaseHistoryMapper.fromFirebasePurchaseHistoryDtoListToPurchaseHistoryList(
                purchaseHistoryDto
            )
            println("Purchase History: $purchaseHistory")

            LoadResult.Page(
                data = purchaseHistory,
                prevKey = null,
                nextKey = if (lastVisible == null) null else user?.run {
                    firestore.collection(Constants.FIREBASE_PURCHASE_HISTORY_COLLECTION)
                        // User history is stored with random id, but also user uid is used to know which user has it
                        .whereEqualTo(Constants.FIREBASE_USERS_USERID, user.uid)
                        .orderBy("date", Query.Direction.DESCENDING)
                        .startAfter(lastVisible)
                        .limit(Constants.FIRESTORE_PAGE_SIZE.toLong())
                        .get()
                        .await()
                }
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }


}