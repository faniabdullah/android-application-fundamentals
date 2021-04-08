package com.bangkit.faniabdullah_bfaa.data.network

import com.bangkit.faniabdullah_bfaa.BuildConfig
import com.bangkit.faniabdullah_bfaa.domain.model.DetailUserResponse
import com.bangkit.faniabdullah_bfaa.domain.model.RepositoriesResponse
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.domain.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUsers(
        @Path("username") username: String,
    ): Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowingUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>


    @GET("users/{username}/repos")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getRepositoriesUsers(
        @Path("username") username: String,
    ): Call<ArrayList<RepositoriesResponse>>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowersUsers(
        @Path("username") username: String,
    ): Call<ArrayList<User>>
}