package com.bangkit.faniabdullah_bfaa.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey
    val id : Int,
    val login : String,
    val avatar_url : String,
    val type: String,
    var isfavorite : Boolean
): Parcelable
