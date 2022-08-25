package com.example.kotlinchat.Activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.Common
import com.google.firebase.auth.FirebaseAuth

class LatestMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_new_msg -> {
                Common.changeActivity(this, NewMessageActivity())
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Common.changeActivity(this, LoginActivity(), true)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
