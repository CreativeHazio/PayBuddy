package com.timeless.paybuddy.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FirebaseRepository {

    suspend fun createUserWithEmailAndPassword(email : String, password: String) : FirebaseUser?

    suspend fun addUserToFirestore(user: User) : Response<String>?

    suspend fun sendEmailVerification()

    suspend fun loginWithEmailAndPassword(email : String, password: String) : FirebaseUser?

    suspend fun getUserFromFirestore() : User?

    fun getUserPurchaseHistoryPagingData() : Flow<PagingData<PurchaseHistory>>


}