package com.example.kotlinchat.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlinchat.Controllers.ControllerSignIn
import com.example.kotlinchat.R
import com.example.kotlinchat.Utils.FragmentUtils


class SignInFragment : Fragment(), View.OnClickListener {

    lateinit var mainView: View
    lateinit var mTxtSignUp: TextView
    lateinit var mEdtxtEmail: EditText
    lateinit var mEdtxtPswd: EditText
    lateinit var mButtonSignIn: Button

    lateinit var controller: ControllerSignIn

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_sign_in, container, false)
        viewsInit()
        viewListeners()
        return mainView
    }

    fun viewsInit() {
        mTxtSignUp = mainView.findViewById(R.id.tv_sign_up)
        mEdtxtEmail = mainView.findViewById(R.id.et_email)
        mEdtxtPswd = mainView.findViewById(R.id.et_pswd)
        mButtonSignIn = mainView.findViewById(R.id.btn_sign_in)
        controller = ControllerSignIn(this.activity as AppCompatActivity)
    }

    fun viewListeners() {
        mTxtSignUp.setOnClickListener(this)
        mButtonSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_sign_up -> activity?.let {
                FragmentUtils.replaceFragment(
                    it,
                    SignUpFragment(),
                    R.id.login_container, true
                )
            }
            R.id.btn_sign_in -> {
                controller.setSignIn(mEdtxtEmail, mEdtxtPswd)

            }

        }
    }
}
