package com.timeless.paybuddy.presentation.shared

sealed class NetworkEvent {
    data object Available : NetworkEvent()
    data class Unavailable(val errorMessage: String) : NetworkEvent()
}