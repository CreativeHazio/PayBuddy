package com.timeless.paybuddy.data.remote.firebase.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.timeless.paybuddy.data.mapper.UserMapper
import com.timeless.paybuddy.data.remote.firebase.PurchaseHistoryPagingSource
import com.timeless.paybuddy.data.remote.firebase.model.FirebaseUserDto
import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.util.Constants
import com.timeless.paybuddy.util.Constants.Companion.FIRESTORE_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class FirebaseRepositoryImpl  @Inject constructor(
    private val firebaseService: FirebaseService,
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {

    //TODO: Check for the best way to catch errors in android
    override suspend fun addUserToFirestore(
        user: User
    ) : Response<String>? {

        try {
            return firebaseService.addUserToFirestore(
                UserMapper.fromUserToFirebaseUserDto(user)
            )
        } catch (e : Exception) {
            Log.w("Error adding user to firestore", e.message.toString())
        }

        return null

    }

    override suspend fun getUserFromFirestore() : User? {

        try {
            val user = auth.currentUser
            user?.run {
                val response = firestore.collection(Constants.FIREBASE_USERS_COLLECTION)
                    .whereEqualTo(Constants.FIREBASE_USERS_USERID, user.uid)
                    .get()
                    .await()
                println(response.documents)

                val firebaseUserDto = response.documents[0].toObject(FirebaseUserDto::class.java)
                firebaseUserDto?.let {
                    return UserMapper.fromFirebaseUserDtoToUser(it)
                }
            }
        } catch (e : Exception) {
            Log.w("Error getting user from firestore", e.message.toString())
        }

        return null
    }

    override fun getUserPurchaseHistoryPagingData(): Flow<PagingData<PurchaseHistory>> {
        return Pager(
            config = PagingConfig(
                pageSize = FIRESTORE_PAGE_SIZE
            )
        ) {
            PurchaseHistoryPagingSource(firestore, auth)
        }.flow
    }

    override suspend fun createUserWithEmailAndPassword(
        email : String,
        password : String
    ): FirebaseUser? {

        try {

            val isUserCreated = auth.createUserWithEmailAndPassword(
                email, password
            ).await()

            return isUserCreated.user

        } catch (e : Exception) {
            Log.w("Error creating user with email and password", e.message.toString())
        }

        return null
    }

    override suspend fun sendEmailVerification() {

        try {
            val user = auth.currentUser
            user?.run {
                user.sendEmailVerification().await()
            }
            println("Verification sent")
        } catch (e : Exception) {
            Log.w("Error sending email verification", e.message.toString())
            println("Error email verification ${e.message.toString()}")
        }

    }

    override suspend fun loginWithEmailAndPassword(email: String, password: String): FirebaseUser? {
        try {
            val loginResponse = auth.signInWithEmailAndPassword(email, password).await()
            return loginResponse.user
        } catch (e : Exception) {
            Log.w("Error logging in with email and password", e.message.toString())
        }

        return null
    }

}