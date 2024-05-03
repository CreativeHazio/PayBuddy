package com.timeless.paybuddy.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.timeless.paybuddy.data.remote.firebase.FirebaseRepositoryImpl
import com.timeless.paybuddy.data.remote.firebase.FirebaseService
import com.timeless.paybuddy.data.remote.flutterwave.FlutterwaveRepositoryImpl
import com.timeless.paybuddy.data.remote.flutterwave.FlutterwaveService
import com.timeless.paybuddy.data.remote.reloadly.ReloadlyRepositoryImpl
import com.timeless.paybuddy.data.remote.reloadly.ReloadlyService
import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.domain.repository.FlutterwaveRepository
import com.timeless.paybuddy.domain.repository.ReloadlyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Provides
    @Singleton
    fun provideFlutterwaveService() : FlutterwaveService {
        return FlutterwaveService.create()
    }

    @Provides
    @Singleton
    fun provideFlutterwaveRepositoryImpl(flutterwaveService: FlutterwaveService) : FlutterwaveRepository {
        return FlutterwaveRepositoryImpl(flutterwaveService)
    }

    @Provides
    @Singleton
    fun provideReloadlyService() : ReloadlyService {
        return ReloadlyService.create()
    }

    @Provides
    @Singleton
    fun provideReloadlyRepositoryImpl(reloadlyService : ReloadlyService) : ReloadlyRepository {
        return ReloadlyRepositoryImpl(reloadlyService)
    }

    @Provides
    @Singleton
    fun provideFirebaseService() : FirebaseService {
        return FirebaseService.create()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepositoryImpl(
        firebaseService: FirebaseService,
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        user: FirebaseUser?
    ) : FirebaseRepository {
        return FirebaseRepositoryImpl(firebaseService, firebaseAuth, firestore, user)
    }

}