package com.example.kotlinchat.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Controllers.ControllerChatLog
import com.example.kotlinchat.Controllers.ControllerNewMessage
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R

class ChatLogActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var controller: ControllerChatLog

    lateinit var userModel: UserModel
    lateinit var mEdtxtMsg: EditText
    lateinit var mButtonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        viewInits()
        userModel = intent.getParcelableExtra(ControllerNewMessage.USER_KEY)!!
        controller = ControllerChatLog(this, userModel)
        supportActionBar?.title = userModel.username
        controller.setMessagesList()
    }

    private fun viewInits() {
        mEdtxtMsg = findViewById(R.id.edtxt_msg)
        mButtonSend = findViewById(R.id.btn_send)
        mButtonSend.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send -> {
                controller.performSendMsg(mEdtxtMsg.text.toString())
                mEdtxtMsg.setText("")
            }
        }
    }


}
