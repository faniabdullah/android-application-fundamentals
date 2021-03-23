package com.bangkit.faniabdullah_bfaa.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    val detailUser = MutableLiveData<DetailUserResponse>()

    fun setSearchDetailUsers(username : String){
        RetrofitClient.apiInstance
            .getDetailUsers(username)
            .enqueue(object  : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    Log.e("respon status","hello"+response)
                    Log.e("respon" , ""+response.body())
                    if (response.isSuccessful){
                        detailUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }
            })
    }

    fun getDetailUser() : LiveData<DetailUserResponse> {
        return detailUser
    }
}