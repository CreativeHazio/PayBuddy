package com.timeless.paybuddy.data.remote.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.util.Constants
import com.timeless.paybuddy.util.Constants.Companion.FIRESTORE_PAGE_SIZE
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject

class FirebaseRepositoryImpl  @Inject constructor(
    private val firebaseService: FirebaseService,
    private val auth : FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val currentUser : FirebaseUser?
) : FirebaseRepository {

    //TODO: Chek for the best way to catch errors in android
    override suspend fun addUserToFirestore(
        user: User
    ) : Response<String>? {

        try {
            return firebaseService.addUserToFirestore(
                user
            )
        } catch (e : Exception) {
            Log.w("Error adding user to firestore", e.message.toString())
        }

        return null

    }

    override suspend fun getUserFromFirestore() : User? {

        try {
            val response = firestore.collection(Constants.FIREBASE_USERS_COLLECTION)
                .whereEqualTo(Constants.FIREBASE_USERS_USERID, currentUser?.uid)
                .get()
                .await()

            return response.documents[0].toObject(User::class.java)
        } catch (e : Exception) {
            Log.w("Error getting user from firestore", e.message.toString())
        }

        return null
    }

    override fun getUserPurchaseHistoryFromFirestore(): Query? {

        try {
            return firestore.collection(Constants.FIREBASE_PURCHASE_HISTORY_COLLECTION)
                // User history is stored with random id, but also user uid is used to know which user has it
                .whereEqualTo(Constants.FIREBASE_USERS_USERID, currentUser?.uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(FIRESTORE_PAGE_SIZE.toLong())
        } catch (e : Exception) {
            Log.w("Error getting user purchase history", e.message.toString())
        }

        return null
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

    override suspend fun sendEmailVerification(): Boolean {

        var isEmailVerificationSent = false

        try {

            currentUser?.sendEmailVerification()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    isEmailVerificationSent = true
                }
            }

        } catch (e : Exception) {
            Log.w("Error sending email verification", e.message.toString())
        }

        return isEmailVerificationSent
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