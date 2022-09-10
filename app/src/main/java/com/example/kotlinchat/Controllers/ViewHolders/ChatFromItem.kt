package com.example.kotlinchat.Controllers.ViewHolders

import android.view.Gravity
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*

class ChatFromItem(val text: String, val userModel: UserModel) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.txt_msg.text = text
    }

}