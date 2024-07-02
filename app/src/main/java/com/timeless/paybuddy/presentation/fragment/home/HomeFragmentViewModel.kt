package com.timeless.paybuddy.presentation.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseUser
import com.timeless.paybuddy.domain.model.User
import com.timeless.paybuddy.domain.model.Wallet
import com.timeless.paybuddy.domain.usecase.user.GetUserInfoUseCase
import com.timeless.paybuddy.presentation.shared.ConnectivityObserver
import com.timeless.paybuddy.presentation.shared.NetworkConnectivityObserver
import com.timeless.paybuddy.presentation.shared.NetworkEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val connectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val networkEvent = Channel<NetworkEvent>()
    val networkEventFlow = networkEvent.receiveAsFlow()

    private val _isLoading = Channel<Boolean>()
    val isLoading = _isLoading.receiveAsFlow()

    private val _isUserBalanceLoading = Channel<Boolean>()
    val isUserBalanceLoading = _isUserBalanceLoading.receiveAsFlow()

    private var _userDetailsMutableFlow = MutableSharedFlow<User>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val userDetails : Flow<User> = _userDetailsMutableFlow.asSharedFlow().buffer(0)

    private var _userBalanceMutableFlow = MutableSharedFlow<Double>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val userBalance : Flow<Double> = _userBalanceMutableFlow.asSharedFlow().buffer(0)

    val purchaseHistoryFlow = getUserInfoUseCase.getUserPurchaseHistory.invoke().cachedIn(viewModelScope)

    init {
        checkConnectionAndLoadUser()
    }

    private fun checkConnectionAndLoadUser() {

        viewModelScope.launch {
            connectivityObserver.observe().collectLatest {
                when(it) {
                    ConnectivityObserver.Status.Available -> {
                        println("Network available from VM")
                        networkEvent.send(NetworkEvent.Available)
                        if (getUserData() == null) {
                            println("I reloaded data again")
                            loadUser()
                        }
                    }
                    ConnectivityObserver.Status.Unavailable -> {
                        // TODO: Load previous user from localdb
                        println("Network unavailable from VM")
                        networkEvent.send(NetworkEvent.Unavailable("Data bundle exhausted!!"))
                        loadFakeUser()
                    }
                    ConnectivityObserver.Status.Lost -> {
                        println("Network lost from VM")
                        networkEvent.send(NetworkEvent.Unavailable("Turn on mobile data!!"))
                        loadFakeUser()
                    }
                    else -> {
                        println("Network losing from VM")
                        networkEvent.send(NetworkEvent.Unavailable("Poor internet connection!!"))
                        loadFakeUser()
                    }
                }
            }
        }
    }

    private fun loadFakeUser() {
        viewModelScope.launch {

            resetLoadingStatus()
            val wallet = Wallet(
                "PayBuddy-Creative",
                "gfgfyrgr846659hgfgjuudjs",
                "Wema",
                "0541561500",
                "Active"
            )

            val user = User(
                "FFD54674874tdh848e",
                "hazio21@gmail.com",
                "CreativeHazio",
                "07019487824",
                "AIRTEL",
                wallet
            )
            _userDetailsMutableFlow.tryEmit(user)
            userDetails.collect{
                println("User from fake user $it")
            }
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            _isLoading.send(true)
            _isUserBalanceLoading.send(true)

            val user = getUserInfoUseCase.getUser()

            if (user != null) {
                _userDetailsMutableFlow.tryEmit(user)
                _isLoading.send(false)
                user.wallet?.accountReference?.let {
                    val userBalance = getUserInfoUseCase.getUserBalance(it).body() ?: 50000.00
                    _userBalanceMutableFlow.tryEmit(userBalance)
                    //TODO: Add User and user balance to RoomDB with same userId
                    _isUserBalanceLoading.send(false)
                }
            } else {
                //TODO: Error?
                networkEvent.send(NetworkEvent.Unavailable("Network unavailable"))
                loadFakeUser()
            }

        }
    }

    fun getUserData() : User? {

        viewModelScope.launch {
            userDetails.collectLatest {
                println("User from getUserData $it")
                return@collectLatest
            }
        }

        return null
    }

    fun reloadAllData() {
        viewModelScope.launch {
            checkConnectionAndLoadUser()
        }
    }

    private fun resetLoadingStatus() {
        viewModelScope.launch {
            _isLoading.send(false)
            _isUserBalanceLoading.send(false)
        }
    }

}