package com.example.kotlinchat.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Controllers.ControllerChatLog
import com.example.kotlinchat.Controllers.ControllerNewMessage
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import java.util.*

class ChatLogActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var controller: ControllerChatLog

    lateinit var userModel: UserModel
    lateinit var mEdtxtMsg: EditText
    lateinit var mTextView: TextView
    lateinit var mButtonSend: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        userModel = intent.getParcelableExtra(ControllerNewMessage.USER_KEY)!!
        controller = ControllerChatLog(this, userModel)
        viewInits()
        controller.setMessagesList()
    }

    private fun viewInits() {
        mEdtxtMsg = findViewById(R.id.edtxt_msg)
        mTextView = findViewById(R.id.title)
        mButtonSend = findViewById(R.id.btn_send)
        mButtonSend.setOnClickListener(this)

        mTextView?.text = userModel.username.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

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
