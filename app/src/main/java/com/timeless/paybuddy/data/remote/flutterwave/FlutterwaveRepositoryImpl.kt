package com.timeless.paybuddy.data.remote.flutterwave

import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.repository.FlutterwaveRepository
import retrofit2.Response
import javax.inject.Inject

class FlutterwaveRepositoryImpl @Inject constructor(
    private val flutterwaveService: FlutterwaveService
) : FlutterwaveRepository {

    override suspend fun createWallet(
        user : User
    ) : Response<String> {
        return flutterwaveService.createWallet(
            user
        )
    }

    override suspend fun loadUserBalance(accountReference: String): Response<Double> {
        return flutterwaveService.loadUserBalance(accountReference)
    }

}