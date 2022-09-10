package com.example.kotlinchat.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

    fun covertTimeToText(timeInMillis: Long?): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = "ago"
        try {
            val dateFormat = Calendar.getInstance()
            val pasTime: Date = dateFormat.also { it.timeInMillis = timeInMillis?:0 }.time
            val nowTime = Date()
            val dateDiff: Long = nowTime.getTime() - pasTime.getTime()
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 60) {
                convTime = "$second sec $suffix"
            } else if (minute < 60) {
                convTime = "$minute min $suffix"
            } else if (hour < 24) {
                convTime = "$hour hr $suffix"
            } else if (day >= 7) {
                convTime = if (day > 360) {
                    (day / 360).toString() + " Years " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " Months " + suffix
                } else {
                    (day / 7).toString() + " Week " + suffix
                }
            } else if (day < 7) {
                convTime = "$day Days $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
            Log.e("ConvTimeE", e.message.toString())
        }
        return convTime
    }

}