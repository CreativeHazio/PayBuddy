package com.timeless.paybuddy.presentation.fragment.userReg

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.timeless.paybuddy.domain.usecase.user.CreateUserUseCase
import com.timeless.paybuddy.domain.usecase.wallet.CreateWalletUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    private val createWalletUseCase: CreateWalletUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {


    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _isEmailVerified = Channel<Boolean>()
    val isEmailVerified = _isEmailVerified.receiveAsFlow()

    private val _isUserExist = Channel<Boolean>()
    val isUserExist = _isUserExist.receiveAsFlow()

    private val _isWalletCreated = Channel<Boolean>()
    val isWalletCreated = _isWalletCreated.receiveAsFlow()

    fun logInWithEmailAndPassword(
        email : String,
        password : String,
    ) {

        viewModelScope.launch {
            _isLoading.send(true)

            val userLogin = createWalletUseCase.loginWithEmailAndPassword(email, password)

            if (userLogin != null) {
                if (isEmailVerified(userLogin)) {
                    //TODO: Check user data in firestore and see if user wallet is not null..
                    // TODO: Something like what i did below
                    val user = createWalletUseCase.getUserFromFirestore()
                    user?.let {
                        if (it.wallet == null) {
                            //TODO: Get response of the wallet and isWalletCreated == true if successful
                            val walletResponse = createWalletUseCase.createWallet(it)
                            _isWalletCreated.send(
                                walletResponse.isSuccessful
                            )
                        } else {
                            //TODO: Go to homepage and isWalletCreated == true
                            _isWalletCreated.send(true)
                        }
                    }

                } else {
                    // User email is not verified yet
                    _isEmailVerified.send(false)
                }
            } else {
                //User does not exist
                _isUserExist.send(false)
            }

            _isLoading.send(false)

        }

    }

    private fun isEmailVerified(user: FirebaseUser) : Boolean {
        return user.isEmailVerified
    }

    fun sendEmailVerification() {
        viewModelScope.launch {
            createUserUseCase.sendEmailVerification()
        }
    }

}