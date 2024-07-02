package com.timeless.paybuddy.data.mapper

import com.timeless.paybuddy.data.remote.firebase.model.FirebaseUserDto
import com.timeless.paybuddy.data.remote.firebase.model.FirebaseWalletDto
import com.timeless.paybuddy.data.remote.flutterwave.model.FlutterwaveUserDto
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

        fun fromUserToFirebaseUserDto(user: User) : FirebaseUserDto {
            return FirebaseUserDto(
                user.userId,
                user.email,
                user.username,
                user.mobileNumber,
                user.provider,
                fromWalletToFirebaseWalletDto(user.wallet)
            )
        }

        fun fromUserToFlutterwaveUserDto(user: User) : FlutterwaveUserDto {
            return FlutterwaveUserDto(
                user.userId,
                "PayBuddy-${user.username.substring(0,2)}",
                user.email,
                user.mobileNumber,
                "NG"
            )
        }

        private fun fromWalletToFirebaseWalletDto(wallet: Wallet?): FirebaseWalletDto? {
            return FirebaseWalletDto(
                wallet?.accountName,
                wallet?.accountReference,
                wallet?.bankName,
                wallet?.nuban,
                wallet?.status
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