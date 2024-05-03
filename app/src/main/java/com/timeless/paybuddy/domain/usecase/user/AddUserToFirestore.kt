package com.timeless.paybuddy.domain.usecase.user

import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import retrofit2.Response

class AddUserToFirestore (
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(user : User): Response<String>? {
        return firebaseRepository.addUserToFirestore(user)
    }

}