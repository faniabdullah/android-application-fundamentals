package com.bangkit.faniabdullah_bfaa.utils.constant

import android.database.Cursor
import com.bangkit.faniabdullah_bfaa.domain.model.User

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        while (cursor?.moveToNext() == true) {
            val id =
                cursor.getInt((cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.ID)))
            val username =
                cursor.getString((cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.USERNAME)))
            val avatarUrl =
                cursor.getString((cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.AVATAR_URL)))
            val typeUser =
                cursor.getString((cursor.getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumns.TYPE)))
            list.add(
                User(
                    username,
                    id,
                    avatarUrl,
                    typeUser,
                    true
                )
            )
        }
        return list
    }
}