package com.timeless.paybuddy.data.remote.reloadly.repository

import com.timeless.paybuddy.domain.model.PurchaseHistory
import com.timeless.paybuddy.util.Constants
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ReloadlyService {

    @POST
    suspend fun buyData (
        @Query("amount")
        amount : Int,
        @Query("accountReference")
        accountReference : String,
        @Body purchaseHistory: PurchaseHistory
    ) : Response<String>

    companion object {

        fun create(): ReloadlyService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return Retrofit.Builder()
                .baseUrl(Constants.CLOUD_FUNCTIONS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ReloadlyService::class.java)
        }
    }
}