package com.timeless.paybuddy.di

import com.timeless.paybuddy.domain.repository.FirebaseRepository
import com.timeless.paybuddy.domain.repository.FlutterwaveRepository
import com.timeless.paybuddy.domain.usecase.user.AddUserToFirestore
import com.timeless.paybuddy.domain.usecase.user.CreateUserUseCase
import com.timeless.paybuddy.domain.usecase.user.CreateUserWithEmailAndPassword
import com.timeless.paybuddy.domain.usecase.user.GetUserBalance
import com.timeless.paybuddy.domain.usecase.user.GetUserFromFirestore
import com.timeless.paybuddy.domain.usecase.user.GetUserInfoUseCase
import com.timeless.paybuddy.domain.usecase.user.GetUserPurchaseHistory
import com.timeless.paybuddy.domain.usecase.user.LoginWithEmailAndPassword
import com.timeless.paybuddy.domain.usecase.user.SendEmailVerification
import com.timeless.paybuddy.domain.usecase.wallet.CreateWallet
import com.timeless.paybuddy.domain.usecase.wallet.CreateWalletUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserInfoUseCase(
        firebaseRepository : FirebaseRepository,
        flutterwaveRepository: FlutterwaveRepository
    ) : GetUserInfoUseCase {
        return GetUserInfoUseCase(
            getUser = GetUserFromFirestore(firebaseRepository),
            getUserBalance = GetUserBalance(flutterwaveRepository),
            getUserPurchaseHistory = GetUserPurchaseHistory(firebaseRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCreateUserUseCase(
        firebaseRepository: FirebaseRepository
    ): CreateUserUseCase {
        return CreateUserUseCase(
            createUserWithEmailAndPassword = CreateUserWithEmailAndPassword(firebaseRepository),
            addUserToFirestore = AddUserToFirestore(firebaseRepository),
            sendEmailVerification = SendEmailVerification(firebaseRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCreateWalletUseCase(
        flutterwaveRepository: FlutterwaveRepository,
        firebaseRepository: FirebaseRepository
    ): CreateWalletUseCase {
        return CreateWalletUseCase(
            loginWithEmailAndPassword = LoginWithEmailAndPassword(firebaseRepository),
            createWallet = CreateWallet(flutterwaveRepository),
            getUserFromFirestore = GetUserFromFirestore(firebaseRepository)
        )
    }

    @Provides
    @Singleton
    fun providesSendEmailVerification(firebaseRepository: FirebaseRepository): SendEmailVerification {
        return SendEmailVerification(firebaseRepository)
    }

}