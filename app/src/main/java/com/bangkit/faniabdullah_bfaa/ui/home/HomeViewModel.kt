package com.bangkit.faniabdullah_bfaa.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.domain.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

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
                        for (item in response.body()?.items!!) {

                        }

                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.e("Failure", it) }
                }

            })
    }

    fun getSearchUser() : LiveData<ArrayList<User>>{
        return listUser
    }
}