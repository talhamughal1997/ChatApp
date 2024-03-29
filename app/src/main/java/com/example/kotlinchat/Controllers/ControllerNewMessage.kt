package com.example.kotlinchat.Controllers

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinchat.Activity.ChatLogActivity
import com.example.kotlinchat.Controllers.ViewHolders.UserItem
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.Common
import com.example.kotlinchat.Utils.CurrentUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_latest_message.*

class ControllerNewMessage(val context: AppCompatActivity) {

    private lateinit var recyclerView: RecyclerView
    lateinit var progressDialog: AlertDialog

    companion object{
        val USER_KEY = "USER_KEY"
    }

    fun setRecyclerView() {
        recyclerView = context.findViewById(R.id.rec_new_msg)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        progressDialog = Common.getProgressDialog(context)
        fetchUsers()
    }

    private fun fetchUsers() {
        progressDialog.show()
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            val adapter = GroupAdapter<GroupieViewHolder>()
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val users = it.getValue(UserModel::class.java)
                    if (users != null && users.uid != CurrentUser.user?.uid) {
                        adapter.add(UserItem(users))
                    }
                    progressDialog.hide()
                }


                adapter.setOnItemClickListener { item, view ->
                    val userItem = item as UserItem
                    val intent = Intent(context,ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY,userItem.user)
                    context.startActivity(intent)
                }

                recyclerView.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {
                progressDialog.hide()
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

        })
    }



}

