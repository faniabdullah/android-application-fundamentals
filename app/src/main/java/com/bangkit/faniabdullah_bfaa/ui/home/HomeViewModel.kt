package com.bangkit.faniabdullah_bfaa.ui.home

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
import com.bangkit.faniabdullah_bfaa.domain.model.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private var userDao : FavoriteUserDao? = null
    private var userDB : UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDao = userDB?.favoriteUserDao()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text


    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query : String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object  : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>,
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }

            })
    }

    fun getSearchUser() : LiveData<ArrayList<User>>{
        return listUser
    }

    fun addToFavorite(username : String , id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                 id,
                 username
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun isFavoriteUser(id: Int) = userDao?.isFavoriteUser(id)

    suspend fun removeFavoriteUser(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }

}