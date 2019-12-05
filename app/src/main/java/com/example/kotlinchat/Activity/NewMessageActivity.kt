package com.example.kotlinchat.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Controllers.ControllerNewMessage
import com.example.kotlinchat.R

class NewMessageActivity : AppCompatActivity() {

    val controller = ControllerNewMessage(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"

        controller.setRecyclerView()
    }
}
