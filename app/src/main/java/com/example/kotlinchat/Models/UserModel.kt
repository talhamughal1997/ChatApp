package com.example.kotlinchat.Models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserModel(val uid:String,val username:String,val profileImageUrl:String) : Parcelable {
    constructor():this("","","")
}