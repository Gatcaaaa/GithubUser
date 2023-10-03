package com.project.githubuser.data.retrofit

import com.project.githubuser.BuildConfig
import com.project.githubuser.data.response.DetailUserResponse
import com.project.githubuser.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @JvmSuppressWildcards
    @GET("users")
    suspend fun getUser(
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): DetailUserResponse

    @JvmSuppressWildcards
    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username : String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): MutableList<UserResponse.ItemsItem>

    @JvmSuppressWildcards
    @GET("search/users")
    suspend fun getSearchUser(
        @QueryMap params: Map<String, Any>,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN
    ): UserResponse
}