package com.example.kotlinchat.Activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.Common
import com.example.kotlinchat.Utils.CurrentUser
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val handler = Handler()
        handler.postDelayed(Runnable {
            changeActivity()
        }, SPLASH_TIME_OUT)
    }

    private fun changeActivity() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            Common.changeActivity(this, LatestMessageActivity())
            finish()
        } else {
            Common.changeActivity(this, LoginActivity())
            finish()
        }
    }
}
