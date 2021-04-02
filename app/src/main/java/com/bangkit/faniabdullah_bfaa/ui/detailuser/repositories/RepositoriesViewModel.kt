package com.bangkit.faniabdullah_bfaa.ui.detailuser.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.faniabdullah_bfaa.data.network.RetrofitClient
import com.bangkit.faniabdullah_bfaa.domain.model.RepositoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesViewModel : ViewModel() {

    val listRepositories = MutableLiveData<ArrayList<RepositoriesResponse>>()


    fun setListRepositories(username: String) {
        RetrofitClient.apiInstance
            .getRepositoriesUsers(username)
            .enqueue(object : Callback<ArrayList<RepositoriesResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<RepositoriesResponse>>,
                    response: Response<ArrayList<RepositoriesResponse>>,
                ) {
                    if (response.isSuccessful) {
                        listRepositories.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<RepositoriesResponse>>, t: Throwable) {
                    Log.e("Failure", "${t.message}")
                }

            })
    }

    fun getRepositories(): LiveData<ArrayList<RepositoriesResponse>> {
        return listRepositories
    }
}