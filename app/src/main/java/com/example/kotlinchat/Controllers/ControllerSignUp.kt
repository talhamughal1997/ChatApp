package com.example.kotlinchat.Controllers

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotlinchat.Activity.LatestMessageActivity
import com.example.kotlinchat.Models.UserModel
import com.example.kotlinchat.Utils.Common
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*
import com.google.android.gms.common.util.IOUtils.toByteArray




class ControllerSignUp(context: AppCompatActivity) {

    lateinit var mEdtxtEmail: EditText
    lateinit var mEdtxtPswd: EditText
    lateinit var mEdtxtUName: EditText
    lateinit var selectedPhotoUri: Uri

    val context = context
    val progressDialog: AlertDialog = Common.getProgressDialog(context)

    private lateinit var uname: String
    private lateinit var email: String
    private lateinit var pswd: String
    private lateinit var auth: FirebaseAuth
    private lateinit var bitmap: Bitmap

    companion object {
        val PICKIMAGEREQUEST = 901
    }


    fun setSignUp(uname: EditText, email: EditText, password: EditText) {
        this.mEdtxtUName = uname
        this.mEdtxtEmail = email
        this.mEdtxtPswd = password

        this.uname = uname.text.toString()
        this.email = email.text.toString()
        this.pswd = password.text.toString()
        auth = FirebaseAuth.getInstance()
        registerAccount()
    }

    private fun registerAccount() {
        if (isEmptyTextView()) {
            progressDialog.show()
            createUserWithEmailPswd()
        }
    }


    fun getSignUpError(task: Task<AuthResult>): Boolean {
        if (!task.isSuccessful) {
            try {
                throw task.exception!!
            } catch (e: FirebaseAuthWeakPasswordException) {
                mEdtxtPswd.setError("Weak Password")
                mEdtxtPswd.requestFocus()
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                mEdtxtEmail.setError("Invalid Email")
                mEdtxtEmail.requestFocus()
            } catch (e: FirebaseAuthUserCollisionException) {
                mEdtxtEmail.setError("User Already Exist")
                mEdtxtEmail.requestFocus()
            } catch (e: Exception) {
                e.message?.let { Log.e(TAG, it) }
            }

        }

        return true;
    }

    fun createUserWithEmailPswd() {
        auth.createUserWithEmailAndPassword(email, pswd).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                uploadImageToFirebase()

            } else {
                getSignUpError(it)
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", it.exception)
                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                progressDialog.hide()
            }
        }
    }

    private fun isEmptyTextView(): Boolean {
        if (uname.isEmpty()) {
            mEdtxtUName.setError("Empty User Name!!")
            return false
        }
        if (email.isEmpty()) {
            mEdtxtEmail.setError("Empty Email!!")
            return false
        }
        if (pswd.isEmpty()) {
            mEdtxtPswd.setError("Empty User Name!!")
            return false
        }
      /*  if (!::selectedPhotoUri.isInitialized) {
            Toast.makeText(context, "Plz Select Profile Photo", Toast.LENGTH_LONG).show()
            return false
        }*/
        return true
    }

    fun selectPhotoFromGallery(fragment: Fragment) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        fragment.startActivityForResult(intent, PICKIMAGEREQUEST)
    }

    fun getBitmapOfSelectedPhoto(intent: Intent): Bitmap {
        selectedPhotoUri = intent.data!!
        bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedPhotoUri)
        bitmap = getResizedBitmap(bitmap,1024)
        return bitmap
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }


    fun uploadImageToFirebase() {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val data = byteArrayOutputStream.toByteArray()


        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putBytes(data)
            .addOnSuccessListener {
                Log.d("Register", "Successfully upload image: ${it.metadata?.path}")




                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                    Log.d("Register", "File Location: $it")
                }
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = UserModel(uid, uname, profileImageUrl)

        ref.setValue(user).addOnSuccessListener {
            progressDialog.hide()
            Common.changeActivity(context, LatestMessageActivity(), true)
            Log.d("Register", "Finally we saved the user to firebase database")
        }
    }

}