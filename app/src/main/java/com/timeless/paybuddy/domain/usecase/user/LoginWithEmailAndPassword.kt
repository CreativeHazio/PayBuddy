package com.timeless.paybuddy.domain.usecase.user

import com.google.firebase.auth.FirebaseUser
import com.timeless.paybuddy.domain.repository.FirebaseRepository

class LoginWithEmailAndPassword(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(email : String, password: String): FirebaseUser? {
        return firebaseRepository.loginWithEmailAndPassword(email, password)
    }

}