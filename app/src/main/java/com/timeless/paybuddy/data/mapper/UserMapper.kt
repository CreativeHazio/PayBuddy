package com.timeless.paybuddy.data.mapper

import com.timeless.paybuddy.data.remote.firebase.model.FirebaseUserDto
import com.timeless.paybuddy.data.remote.firebase.model.FirebaseWalletDto
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.model.Wallet

class UserMapper {

    companion object {

        fun fromFirebaseUserDtoToUser(firebaseUserDto: FirebaseUserDto) : User {
            return User(
                firebaseUserDto.userId!!,
                firebaseUserDto.email!!,
                firebaseUserDto.username!!,
                firebaseUserDto.mobileNumber!!,
                firebaseUserDto.provider!!,
                fromFirebaseWalletDtoToWallet(firebaseUserDto.wallet!!)
            )
        }

        private fun fromFirebaseWalletDtoToWallet(firebaseWalletDto: FirebaseWalletDto) : Wallet {
            return Wallet(
                firebaseWalletDto.accountName!!,
                firebaseWalletDto.accountReference!!,
                firebaseWalletDto.bankName!!,
                firebaseWalletDto.nuban!!,
                firebaseWalletDto.status!!
            )
        }

    }
}