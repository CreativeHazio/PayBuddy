package com.timeless.paybuddy.domain.usecase.wallet

import com.timeless.paybuddy.domain.usecase.user.GetUserFromFirestore
import com.timeless.paybuddy.domain.usecase.user.LoginWithEmailAndPassword

data class CreateWalletUseCase(
    val loginWithEmailAndPassword: LoginWithEmailAndPassword,
    val createWallet: CreateWallet,
    val getUserFromFirestore: GetUserFromFirestore
)
