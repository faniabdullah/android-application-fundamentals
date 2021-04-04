package com.bangkit.faniabdullah_bfaa.ui.detailuser.followers

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUser
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUserDao
import com.bangkit.faniabdullah_bfaa.data.local.UserDatabase
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao? = null
    private var userDB: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDB?.favoriteUserDao()
    }

    private val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowersUsers(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>,
                ) {
                    if (response.isSuccessful) {
                        setFavoriteUser(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }
            })
    }

    fun getFollowers(): LiveData<ArrayList<User>> {
        return listFollowers
    }

    fun setFavoriteUser(data: ArrayList<User>?) {
        val listUserCheckedFavorite = ArrayList<User>()
        if (data != null) {
            CoroutineScope(Dispatchers.IO).launch {
                for (list in data) {
                    var stateChecked = false
                    val count = isFavoriteUser(list.id)
                    withContext(Dispatchers.Main) {
                        if (count != null) {
                            stateChecked = count > 0
                        }
                        list.isfavorite = stateChecked
                        listUserCheckedFavorite.add(list)
                    }
                }

                if (listUserCheckedFavorite.size >= data.size) {
                    listFollowers.postValue(listUserCheckedFavorite)
                }
            }
        }
    }


    fun addToFavorite(data: User) {
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


    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }
}