package com.example.kotlinchat.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Controllers.ControllerNewMessage
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.CurrentUser

class NewMessageActivity : AppCompatActivity() {

    val controller = ControllerNewMessage(this)

    companion object {
        val USER_KEY = "USER_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"
        controller.setRecyclerView()
    }
}
