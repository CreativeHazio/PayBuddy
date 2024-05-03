package com.timeless.paybuddy.domain.usecase.user

data class CreateUserUseCase(
    val createUserWithEmailAndPassword: CreateUserWithEmailAndPassword,
    val addUserToFirestore: AddUserToFirestore,
    val sendEmailVerification: SendEmailVerification
)
