package com.timeless.paybuddy.domain.usecase.user

import com.timeless.paybuddy.domain.repository.FlutterwaveRepository
import retrofit2.Response

class GetUserBalance(private val flutterwaveRepository: FlutterwaveRepository) {

    suspend operator fun invoke(accountReference : String): Response<Double> {
        return flutterwaveRepository.loadUserBalance(accountReference)
    }

}