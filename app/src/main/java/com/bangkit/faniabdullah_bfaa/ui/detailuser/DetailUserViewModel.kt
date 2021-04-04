package com.bangkit.faniabdullah_bfaa.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUser
import com.bangkit.faniabdullah_bfaa.data.local.FavoriteUserDao
import com.bangkit.faniabdullah_bfaa.data.local.UserDatabase
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.DetailUserResponse
import com.bangkit.faniabdullah_bfaa.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {

    val detailUser = MutableLiveData<DetailUserResponse>()
    val isSuccess = MutableLiveData<Boolean>()
    private var userDao: FavoriteUserDao? = null
    private var userDB: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDB?.favoriteUserDao()
    }


    fun setSearchDetailUsers(username: String) {
        RetrofitClient.apiInstance
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        detailUser.postValue(response.body())
                        isSuccess.postValue(true)
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                    isSuccess.postValue(false)
                }
            })
    }


    fun addToFavorite(data: User) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                data.id,
                data.login,
                data.avatar_url,
                data.type,
                data.isfavorite
            )
            userDao?.addToFavorite(user)
        }
    }

    fun isFavoriteUser(id: Int) = userDao?.isFavoriteUser(id)


    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return detailUser
    }

    fun checkStatusServer(): MutableLiveData<Boolean> {
        return isSuccess
    }
}