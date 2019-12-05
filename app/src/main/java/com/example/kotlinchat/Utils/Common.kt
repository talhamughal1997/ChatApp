package com.example.kotlinchat.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object Common {

    fun changeActivity(appCompatActivity: AppCompatActivity, second_activity: AppCompatActivity) {
        val intent = Intent(appCompatActivity, second_activity::class.java)
        appCompatActivity.startActivity(intent)
    }

    fun changeActivity(appCompatActivity: AppCompatActivity, second_activity: AppCompatActivity, clearStacks: Boolean) {
        val intent = Intent(appCompatActivity, second_activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        appCompatActivity.startActivity(intent)
    }

    fun getProgressBar(context: Context): ProgressBar {
        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleSmall)
        return progressBar
    }

    fun getProgressDialog(activity: Activity): AlertDialog {
        return AlertDialog.Builder(activity).apply {
            val dialogView = activity.layoutInflater.inflate(com.example.kotlinchat.R.layout.progress_dialog, null)
            val txtview = dialogView.findViewById<TextView>(com.example.kotlinchat.R.id.loading_msg)

            setView(dialogView)
            setCancelable(false)
        }.create()
    }

}