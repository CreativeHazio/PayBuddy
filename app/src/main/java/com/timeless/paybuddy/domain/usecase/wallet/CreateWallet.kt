package com.timeless.paybuddy.domain.usecase.wallet

import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FlutterwaveRepository
import retrofit2.Response

class CreateWallet (
    private val flutterwaveRepository: FlutterwaveRepository
) {

    suspend operator fun invoke(user: User): Response<String> {
        return flutterwaveRepository.createWallet(user)
    }

}