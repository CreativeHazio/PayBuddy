package com.timeless.paybuddy.domain.repository

import com.timeless.paybuddy.domain.model.User
import retrofit2.Response

interface FlutterwaveRepository {

    suspend fun createWallet(user : User) : Response<String>

    suspend fun loadUserBalance(accountReference : String) : Response<Double>

}