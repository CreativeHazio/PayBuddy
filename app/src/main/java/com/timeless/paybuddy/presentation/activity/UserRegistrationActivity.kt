package com.timeless.paybuddy.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timeless.paybuddy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)
    }
}