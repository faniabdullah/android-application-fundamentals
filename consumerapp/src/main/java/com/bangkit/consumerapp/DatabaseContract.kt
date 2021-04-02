package com.bangkit.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.bangkit.faniabdullah_bfaa"
    const val SCHEME = "content"

    internal class FavoriteUserColumns : BaseColumns {
        companion object {
            private const val TABLE_NAME = "favorite_user"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"
            const val TYPE = "type"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }


}