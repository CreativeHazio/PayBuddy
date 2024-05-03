package com.timeless.paybuddy.data.remote.firebase

import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.util.Constants
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface FirebaseService {

    @POST
    suspend fun addUserToFirestore(
        @Body user: User
    ) : Response<String>

    companion object {

        fun create(): FirebaseService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return Retrofit.Builder()
                .baseUrl(Constants.CLOUD_FUNCTIONS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FirebaseService::class.java)
        }
    }
}