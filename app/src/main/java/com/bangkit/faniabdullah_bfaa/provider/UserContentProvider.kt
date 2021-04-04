package com.bangkit.faniabdullah_bfaa.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUserDao
import com.bangkit.faniabdullah_bfaa.data.local.UserDatabase

class UserContentProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.bangkit.faniabdullah_bfaa"
        private const val TABLE_NAME = "favorite_user"
        const val ID_FAVORITE_USER_DATA = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORITE_USER_DATA)
        }
    }

    private var userDao: FavoriteUserDao? = null

    override fun onCreate(): Boolean {
        userDao = context.let { ctx ->
            ctx?.let { UserDatabase.getDatabase(it)?.favoriteUserDao() }
        }
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            ID_FAVORITE_USER_DATA -> {
                cursor = userDao?.findAll()
                if (context != null) {
                    cursor?.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        return 0
    }
}