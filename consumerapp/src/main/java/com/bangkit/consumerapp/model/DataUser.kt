package com.bangkit.consumerapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val type: String,
    var isfavorite: Boolean,
) : Parcelable
