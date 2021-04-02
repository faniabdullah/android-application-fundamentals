package com.bangkit.consumerapp

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<DataUser> {
        val list = ArrayList<DataUser>()
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
                DataUser(
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