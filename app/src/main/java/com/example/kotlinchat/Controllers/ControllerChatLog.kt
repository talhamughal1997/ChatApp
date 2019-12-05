package com.example.kotlinchat.Controllers

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.Activity.LatestMessageActivity
import com.example.kotlinchat.Controllers.Adapters.ViewHolders.ChatFromItem
import com.example.kotlinchat.Controllers.Adapters.ViewHolders.ChatToItem
import com.example.kotlinchat.Models.ChatMessageModel
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
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

    fun setMessagesList() {
        recyclerview_chat_log = context.findViewById(R.id.rec_chat_msg)
        listenForMessages()
        recyclerview_chat_log.adapter = adapter
    }

    fun performSendMsg(toId: String, msg: String) {

        if (fromId == null) {
            return
        }
        val fromRef = FirebaseDatabase.getInstance().getReference("/messages/user-messages/$fromId/$toId").push()
        val toRef = FirebaseDatabase.getInstance().getReference("/messages/user-messages/$toId/$fromId").push()
        val chatMessage = ChatMessageModel(fromRef.key.toString(), msg, fromId, toId, System.currentTimeMillis() / 1000)
        /*  reference.setValue(chatMessage)
              .addOnSuccessListener {
                  Log.d(TAG, "Saved our chat msg ${ref.key}")
              }*/
        fromRef.setValue(chatMessage)
        toRef.setValue(chatMessage)

    }


    private fun listenForMessages() {
        val toId = userModel.uid
        val ref = FirebaseDatabase.getInstance().getReference("/messages/user-messages/$fromId/$toId")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMsg = p0.getValue(ChatMessageModel::class.java)
                if (chatMsg != null) {
                    if (chatMsg.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessageActivity.currentUser?: return
                        adapter.add(ChatFromItem(chatMsg.text, currentUser))
                        adapter.notifyItemInserted(adapter.itemCount - 1)
                        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                    } else {
                        adapter.add(ChatToItem(chatMsg.text, userModel))
                        adapter.notifyItemInserted(adapter.itemCount - 1)
                        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                    }
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }
}