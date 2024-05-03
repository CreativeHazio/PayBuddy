package com.timeless.paybuddy.domain.usecase.user

import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FirebaseRepository

class GetUserFromFirestore (
    private val firebaseRepository : FirebaseRepository
) {

    suspend operator fun invoke(): User? {
        return firebaseRepository.getUserFromFirestore()
    }

}