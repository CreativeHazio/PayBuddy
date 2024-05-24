package com.timeless.paybuddy.presentation.fragment.userReg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.usecase.user.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFragmentViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _isEmailExistsFlow = Channel<Boolean>()
    val isEmailExists = _isEmailExistsFlow.receiveAsFlow()

    private val _isEmailVerificationSentFlow = Channel<Boolean>()
    val isEmailVerificationSent = _isEmailVerificationSentFlow.receiveAsFlow()

    fun signUpWithEmailAndPassword(
        email : String,
        userName : String,
        password : String,
        mobileNumber: String,
        provider : String
    ) {
        viewModelScope.launch {

            val user = createUserUseCase
                .createUserWithEmailAndPassword(email, password)

            if (user != null) {
                // TODO: Send user data to cloud function to check if email exist, add user and then send verification link
                val uid = user.uid

                val newUser = User(
                    uid,
                    email,
                    userName,
                    mobileNumber,
                    provider,
                    null
                )

                // TODO: Call Cloud Function to add user data to Firestore
                //TODO: How to attach network event and success and failed on every usecase
                val addUserToFirestoreResponse = createUserUseCase.addUserToFirestore(newUser)
                if (addUserToFirestoreResponse?.body() == "Success") {
                    sendEmailVerification()
                } else {
                    //TODO: Error
                }

            } else {
                _isEmailExistsFlow.send(true)
            }

            _isLoading.send(false)

        }
    }

    private fun sendEmailVerification() {
        viewModelScope.launch {
            createUserUseCase.sendEmailVerification()
            _isEmailVerificationSentFlow.send(true)
        }
    }

}