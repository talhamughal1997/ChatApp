package com.example.kotlinchat.Controllers.ViewHolders

import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.user_list_item.view.*
import java.util.*


class UserItem(val user: UserModel) : Item<GroupieViewHolder>() {

    override fun getLayout(): Int {
        //To change body of created functions use File | Settings | File Templates.
        return R.layout.user_list_item
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.uname.text = user.username.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
        Picasso.get().load(user.profileImageUrl)
            .into(viewHolder.itemView.profile_img)

    }

}
