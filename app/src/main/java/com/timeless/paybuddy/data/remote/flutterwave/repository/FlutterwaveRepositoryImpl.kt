package com.timeless.paybuddy.data.remote.flutterwave.repository

import com.timeless.paybuddy.data.mapper.UserMapper
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
            UserMapper.fromUserToFlutterwaveUserDto(user)
        )
    }

    override suspend fun loadUserBalance(accountReference: String): Response<Double> {
        return Response.success(50000.00)
    }

}