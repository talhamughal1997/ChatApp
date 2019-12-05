package com.example.kotlinchat.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R
import com.example.kotlinchat.Fragments.SignInFragment
import com.example.kotlinchat.Utils.FragmentUtils
import com.google.firebase.FirebaseApp

class LoginActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.supportActionBar?.hide()
        FragmentUtils.addFragment(this,
            SignInFragment(),
            R.id.login_container,false)
    }



}
