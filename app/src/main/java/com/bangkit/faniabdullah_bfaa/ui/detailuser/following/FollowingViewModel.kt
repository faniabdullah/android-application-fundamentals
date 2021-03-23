package com.bangkit.faniabdullah_bfaa.ui.detailuser.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowingUsers(username)
            .enqueue(object  : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>,
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failure" , "${t.message}")
                }
            })
    }

    fun getFollowing() : LiveData<ArrayList<User>> {
        return listFollowing
    }
}