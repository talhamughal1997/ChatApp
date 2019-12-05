package com.example.kotlinchat.Controllers

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinchat.Activity.LatestMessageActivity
import com.example.kotlinchat.Utils.Common
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException


class ControllerSignIn(val context: AppCompatActivity) {

    private val auth = FirebaseAuth.getInstance()
    lateinit var edtxtEmail: EditText
    lateinit var edtxtPswd: EditText

    val progressDialog = Common.getProgressDialog(context)


    fun setSignIn(edtxtEmail: EditText, edtxtPswd: EditText) {
        progressDialog.show()
        val email = edtxtEmail.text.toString()
        val pswd = edtxtPswd.text.toString()

        this.edtxtEmail = edtxtEmail
        this.edtxtPswd = edtxtPswd

        if (!isEmptyEdtxt()) {
            signInWithEmailPswd(email, pswd)
        }
    }

    private fun signInWithEmailPswd(email: String, pswd: String) {
        auth.signInWithEmailAndPassword(email, pswd)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Common.changeActivity(context, LatestMessageActivity(), true)
                    progressDialog.hide()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", it.exception)
                    getSignInException(it)
                    progressDialog.hide()
                }
            }
    }

    private fun getSignInException(task: Task<AuthResult>) {
        var errorCode: String

        if (task.exception is FirebaseAuthException) {
            errorCode = (task.exception as FirebaseAuthException).errorCode

            when (errorCode) {

                "ERROR_INVALID_CUSTOM_TOKEN" -> {
                    Toast.makeText(
                        this.context,
                        "The custom token format is incorrect. Please check the documentation.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_CUSTOM_TOKEN_MISMATCH" -> {
                    Toast.makeText(
                        this.context,
                        "The custom token corresponds to a different audience.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_INVALID_CREDENTIAL" -> {
                    Toast.makeText(
                        this.context,
                        "The supplied auth credential is malformed or has expired.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_INVALID_EMAIL" -> {
                    Toast.makeText(this.context, "The email address is badly formatted.", Toast.LENGTH_LONG).show()
                    edtxtEmail.setError("The email address is badly formatted.")
                    edtxtEmail.requestFocus()
                }

                "ERROR_WRONG_PASSWORD" -> {
                    Toast.makeText(
                        this.context,
                        "The password is invalid or the user does not have a password.",
                        Toast.LENGTH_LONG
                    ).show()
                    edtxtPswd.setError("password is incorrect ")
                    edtxtPswd.requestFocus()
                    edtxtPswd.setText("")
                }

                "ERROR_USER_MISMATCH" -> {
                    Toast.makeText(
                        this.context,
                        "The supplied credentials do not correspond to the previously signed in user.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_REQUIRES_RECENT_LOGIN" -> {
                    Toast.makeText(
                        this.context,
                        "This operation is sensitive and requires recent authentication. Log in again before retrying this request.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                    Toast.makeText(
                        this.context,
                        "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_EMAIL_ALREADY_IN_USE" -> {
                    Toast.makeText(
                        this.context,
                        "The email address is already in use by another account.   ",
                        Toast.LENGTH_LONG
                    ).show()
                    edtxtEmail.setError("The email address is already in use by another account.")
                    edtxtEmail.requestFocus()
                }

                "ERROR_CREDENTIAL_ALREADY_IN_USE" -> {
                    Toast.makeText(
                        this.context,
                        "This credential is already associated with a different user account.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_USER_DISABLED" -> {
                    Toast.makeText(
                        this.context,
                        "The user account has been disabled by an administrator.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_USER_TOKEN_EXPIRED" -> {
                    Toast.makeText(
                        this.context,
                        "The user\\'s credential is no longer valid. The user must sign in again.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_USER_NOT_FOUND" -> {
                    Toast.makeText(
                        this.context,
                        "There is no user record corresponding to this identifier. The user may have been deleted.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_INVALID_USER_TOKEN" -> {
                    Toast.makeText(
                        this.context,
                        "The user\\'s credential is no longer valid. The user must sign in again.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_OPERATION_NOT_ALLOWED" -> {
                    Toast.makeText(
                        this.context,
                        "This operation is not allowed. You must enable this service in the console.",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "ERROR_WEAK_PASSWORD" -> {
                    Toast.makeText(this.context, "The given password is invalid.", Toast.LENGTH_LONG).show()
                    edtxtPswd.setError("The password is invalid it must 6 characters at least")
                    edtxtPswd.requestFocus()
                }
            }
        }
    }

    private fun isEmptyEdtxt(): Boolean {
        if (edtxtEmail.text.toString().trim().equals("")) {
            edtxtEmail.setError("Empty Email")
            progressDialog.hide()
            return true
        }
        if (edtxtPswd.text.toString().trim().equals("")) {
            edtxtPswd.setError("Empty Password")
            progressDialog.hide()
            return true
        }
        return false
    }
}