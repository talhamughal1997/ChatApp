package com.example.kotlinchat.Controllers

import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.Controllers.ViewHolders.ChatFromItem
import com.example.kotlinchat.Controllers.ViewHolders.ChatToItem
import com.example.kotlinchat.Models.ChatMessageModel
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.CurrentUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ControllerChatLog(val context: AppCompatActivity, val userModel: UserModel) {

    companion object {
        val TAG = "ChatLogActivity"
    }

    lateinit var recyclerview_chat_log: RecyclerView
    val adapter = GroupAdapter<GroupieViewHolder>()

    val fromId = FirebaseAuth.getInstance().uid
    var toId: String? = null
    var toUser: UserModel? = null

    init {
        toId = userModel.uid
    }

    fun setMessagesList() {
        recyclerview_chat_log = context.findViewById(R.id.rec_chat_msg)
        recyclerview_chat_log.setHasFixedSize(true)
        recyclerview_chat_log.setItemViewCacheSize(20)
        recyclerview_chat_log.setDrawingCacheEnabled(true)
        recyclerview_chat_log.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH)

        listenForMessages()

        recyclerview_chat_log.adapter = adapter
    }

    fun performSendMsg(msg: String) {


        if (fromId == null) return


        //    val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference =
            FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()

        val toReference =
            FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val chatMessage = ChatMessageModel(
            reference.key!!,
            msg.trim(),
            fromId ?: "",
            toId ?: "",
            System.currentTimeMillis() / 1000
        )

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
            }

        toReference.setValue(chatMessage)

        val latestMessageRef =
            FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef =
            FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)

    }


    private fun listenForMessages() {

        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessageModel::class.java)

                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = CurrentUser.user ?: return
                        adapter.add(ChatToItem(chatMessage.text, currentUser))
                    } else {
                        adapter.add(ChatFromItem(chatMessage.text, userModel))
                    }
                }

                recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })
    }
}