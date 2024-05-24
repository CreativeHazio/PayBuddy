package com.timeless.paybuddy.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.timeless.paybuddy.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val auth = Firebase.auth
        //TODO Check internet connection and Go to onboarding or main depending on sign in status
        binding.apply {
            appNameTxt.alpha = 0f
            appNameTxt.animate().setDuration(1500).alpha(1f).withEndAction{
                if (auth.currentUser != null && auth.currentUser!!.isEmailVerified) {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                } else {
                    val intent = Intent(this@SplashScreenActivity, UserRegistrationActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }

            }
        }
    }
}