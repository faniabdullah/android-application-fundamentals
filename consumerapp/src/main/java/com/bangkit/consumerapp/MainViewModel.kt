package com.bangkit.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bangkit.consumerapp.model.DataUser
import com.bangkit.consumerapp.utils.DatabaseContract
import com.bangkit.consumerapp.utils.MappingHelper

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var list = MutableLiveData<ArrayList<DataUser>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumns.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): MutableLiveData<ArrayList<DataUser>> {
        return list
    }

}