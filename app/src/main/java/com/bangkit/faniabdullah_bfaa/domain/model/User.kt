package com.bangkit.faniabdullah_bfaa.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val type: String,
    var isfavorite: Boolean,
) : Parcelable