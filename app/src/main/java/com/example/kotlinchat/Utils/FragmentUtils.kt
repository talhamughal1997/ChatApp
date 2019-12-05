package com.example.kotlinchat.Utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.R


object FragmentUtils {

    private val TAG = "FragmentsManager"
    private var transaction: FragmentTransaction? = null
    //    Return Fragment Transaction
    private fun getTransaction(activity: Activity): FragmentTransaction {
        return getFragmentManager(activity).beginTransaction()
    }

    private fun getFragmentManager(activity: Activity): FragmentManager {
        return (activity as AppCompatActivity).supportFragmentManager
    }


    fun addFragment(activity: Activity, fragment: Fragment, id: Int, add_to_backstack: Boolean) {
        transaction =
            getTransaction(activity)
        transaction!!.add(id, fragment, fragment::class.java.name)
        if (add_to_backstack)
            transaction!!.addToBackStack(fragment::class.java.name)
        transaction!!.commit()
    }


    fun replaceFragment(activity: Activity, fragment: Fragment, id: Int, add_to_backstack: Boolean) {
        val check_Fragment = getFragmentManager(activity).findFragmentByTag(fragment::class.java.name)
        if (check_Fragment == null) {
            transaction = getTransaction(
                activity
            )
                .setCustomAnimations(
                    R.anim.enter_anim,
                    R.anim.exit_anim,
                    R.anim.pop_enter,
                    R.anim.pop_exit
                )
                .replace(id, fragment, fragment::class.java.name)

            if (add_to_backstack) {
                transaction!!.addToBackStack(fragment::class.java.name)
            }
            transaction!!.commit()
        } else {
            transaction =
                getTransaction(activity)
            transaction!!.replace(id, check_Fragment, check_Fragment!!::class.java.name)
                .setCustomAnimations(
                    R.anim.enter_anim,
                    R.anim.exit_anim,
                    R.anim.pop_exit,
                    R.anim.pop_enter
                )
                .addToBackStack(null)
                .commit()
        }
    }

}