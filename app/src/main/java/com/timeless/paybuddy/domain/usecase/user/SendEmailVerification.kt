package com.timeless.paybuddy.domain.usecase.user

import com.timeless.paybuddy.domain.repository.FirebaseRepository

class SendEmailVerification(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke() {
        return firebaseRepository.sendEmailVerification()
    }

}