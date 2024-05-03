package com.timeless.paybuddy.domain.usecase.user

data class GetUserInfoUseCase (
    val getUser: GetUserFromFirestore,
    val getUserBalance: GetUserBalance,
    val getUserPurchaseHistory: GetUserPurchaseHistory
)