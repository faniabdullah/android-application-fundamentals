package com.bangkit.faniabdullah_bfaa.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserResponse(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val name: String,
    val type: String,
    val followers_url: String,
    val following_url: String,
    val followers: Int,
    val following: Int,
    val repos_url: String,
    val location: String,
    val bio: String,
) : Parcelable
