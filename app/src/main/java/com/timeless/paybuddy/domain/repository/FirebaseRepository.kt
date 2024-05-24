package com.timeless.paybuddy.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.Query
import com.timeless.paybuddy.domain.model.User
import retrofit2.Response

interface FirebaseRepository {

    suspend fun createUserWithEmailAndPassword(email : String, password: String) : FirebaseUser?

    suspend fun addUserToFirestore(user: User) : Response<String>?

    suspend fun sendEmailVerification()

    suspend fun loginWithEmailAndPassword(email : String, password: String) : FirebaseUser?

    suspend fun getUserFromFirestore() : User?

    fun getUserPurchaseHistoryFromFirestore(): Query?


}