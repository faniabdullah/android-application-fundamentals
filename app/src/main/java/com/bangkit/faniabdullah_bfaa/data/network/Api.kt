package com.bangkit.faniabdullah_bfaa.data.network

import com.bangkit.faniabdullah_bfaa.domain.model.DetailUserResponse
import com.bangkit.faniabdullah_bfaa.domain.model.User
import com.bangkit.faniabdullah_bfaa.domain.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: 6f450176356630e2240ca9a8929a322f628c1bb1")
    fun getSearchUsers(
        @Query("q") query: String
        ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: 6f450176356630e2240ca9a8929a322f628c1bb1")
    fun getDetailUsers(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/following")
    @Headers("Authorization: 6f450176356630e2240ca9a8929a322f628c1bb1")
    fun getFollowingUsers(
        @Path("username") username: String
    ) : Call<ArrayList<User>>

    @GET("users/{username}/followers")
    @Headers("Authorization: 6f450176356630e2240ca9a8929a322f628c1bb1")
    fun getFollowersUsers(
        @Path("username") username: String
    ) : Call<ArrayList<User>>
}