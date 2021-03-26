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
import kotlinx.coroutines.withContext
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

                        setFavoriteUser(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }
            })
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
                    listUser.postValue(listUserCheckedFavorite)
                }
            }
        }
    }

    fun getSearchUser() : LiveData<ArrayList<User>>{
        return listUser
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

    suspend fun isFavoriteUser(id: Int) = userDao?.isFavoriteUser(id)


    fun removeFavoriteUser(id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeUserFavorites(id)
        }
    }
}