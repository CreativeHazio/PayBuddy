package com.timeless.paybuddy.data.remote.flutterwave.repository

import com.timeless.paybuddy.data.remote.flutterwave.model.FlutterwaveUserDto
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.util.Constants
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface FlutterwaveService {

    @POST("createWallet")
    suspend fun createWallet(
        @Body flutterwaveUserDto: FlutterwaveUserDto
    ) : Response<String>

    @POST("checkBalance")
    suspend fun loadUserBalance(
        accountReference : String
    ) : Response<Double>

    companion object {

        fun create(): FlutterwaveService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return Retrofit.Builder()
                .baseUrl(Constants.CLOUD_FUNCTIONS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FlutterwaveService::class.java)
        }
    }

}