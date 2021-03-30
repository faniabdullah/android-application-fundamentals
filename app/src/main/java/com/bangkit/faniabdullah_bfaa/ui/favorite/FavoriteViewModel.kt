package com.bangkit.faniabdullah_bfaa.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUser
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUserDao
import com.bangkit.faniabdullah_bfaa.data.local.UserDatabase
import com.bangkit.faniabdullah_bfaa.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    fun addToFavorite(data : User){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                data.id,
                data.login,
                data.avatar_url,
                data.type,
                true
            )
            userDao?.addToFavorite(user)
        }
    }

    private fun isFavoriteUser(id: Int) = userDao?.isFavoriteUser(id)


    fun removeFavoriteUser(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }
}