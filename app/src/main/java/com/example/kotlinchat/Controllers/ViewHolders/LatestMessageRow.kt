package com.example.kotlinchat.Controllers.ViewHolders

import com.example.kotlinchat.Models.ChatMessageModel
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.latest_message_row.view.*
import java.util.*


class LatestMessageRow(val chatMessage: ChatMessageModel) : Item<GroupieViewHolder>() {
    var chatPartnerUser: UserModel? = null
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview_latest_message.text = chatMessage.text

        val chatPartnerId: String = if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatMessage.toId
        } else {
            chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                chatPartnerUser = p0.getValue(UserModel::class.java)

                viewHolder.itemView.username_textview_latest_message.text =
                    chatPartnerUser?.username?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }

                viewHolder.itemView.tv_time_ago.text =
                    Common.covertTimeToText(chatMessage.timestamp * 1000)

                val targetImageView = viewHolder.itemView.imageview_latest_message
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}