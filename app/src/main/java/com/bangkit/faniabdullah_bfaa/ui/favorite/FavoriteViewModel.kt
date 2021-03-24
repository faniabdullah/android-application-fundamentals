package com.bangkit.faniabdullah_bfaa.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUser
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUserDao
import com.bangkit.faniabdullah_bfaa.data.local.UserDatabase
import com.bangkit.faniabdullah_bfaa.domain.model.User

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {

    private var userDao : FavoriteUserDao? = null
    private var userDB : UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    fun getFavoriteUser() : LiveData<List<FavoriteUser>>? {
     return userDao?.getFavoriteUser()
    }
}