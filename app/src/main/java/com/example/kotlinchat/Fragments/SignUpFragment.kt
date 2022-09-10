package com.example.kotlinchat.Fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.kotlinchat.Activity.LatestMessageActivity
import com.example.kotlinchat.Controllers.ControllerSignUp
import com.example.kotlinchat.R
import de.hdodenhof.circleimageview.CircleImageView

class SignUpFragment : Fragment(), View.OnClickListener {

    lateinit var mainView: View
    lateinit var controller: ControllerSignUp
    lateinit var mEdtxtUName: EditText
    lateinit var mEdtxtEmail: EditText
    lateinit var mEdtxtPswd: EditText
    lateinit var mTxtViewLogin: TextView
    lateinit var mButtonRegister: Button
    lateinit var mUploadProfileImg: CircleImageView

    lateinit var uname: String
    lateinit var email: String
    lateinit var pswd: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(R.layout.fragment_sign_up, container, false)
        viewsInits()
        viewsListeners()
        varInits()
        return mainView
    }

    fun viewsInits() {
        mEdtxtUName = mainView.findViewById(R.id.et_username)
        mEdtxtEmail = mainView.findViewById(R.id.et_email)
        mEdtxtPswd = mainView.findViewById(R.id.et_pswd)
        mTxtViewLogin = mainView.findViewById(R.id.tv_sign_in)
        mButtonRegister = mainView.findViewById(R.id.btn_sign_up)
        mUploadProfileImg = mainView.findViewById(R.id.iv_profile)
    }

    fun varInits() {
        controller = ControllerSignUp(this.activity as AppCompatActivity)
        uname = mEdtxtUName?.text.toString()
        email = mEdtxtEmail?.text.toString()
        pswd = mEdtxtPswd?.text.toString()
    }

    fun viewsListeners() {
        mButtonRegister.setOnClickListener(this)
        mUploadProfileImg.setOnClickListener(this)
        mTxtViewLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_sign_up -> {
               controller.setSignUp(mEdtxtUName, mEdtxtEmail, mEdtxtPswd)

            }
            R.id.tv_sign_in -> {
               requireActivity().supportFragmentManager.popBackStack()

            }
            R.id.iv_profile -> {
                controller.selectPhotoFromGallery(this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ControllerSignUp.PICKIMAGEREQUEST && resultCode == Activity.RESULT_OK && data != null) {
            mUploadProfileImg.setImageBitmap(controller.getBitmapOfSelectedPhoto(data))
        }
    }



}