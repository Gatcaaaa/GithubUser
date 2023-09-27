package com.project.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.githubuser.data.retrofit.ApiConfig
import com.project.githubuser.ui.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {
    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()

    fun getDetailUser(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getDetailUser(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultDetailUser.value =Result.Error(it)
            }.collect{
                resultDetailUser.value = Result.Success(it)
            }
        }
    }
    fun getFollowers(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getFollowers(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowers.value = Result.Error(it)
            }.collect{
                resultFollowers.value = Result.Success(it)
            }
        }
    }
    fun getFollowing(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getFollowing(username)

                emit(response)
            }.onStart {
                resultDetailUser.value = Result.Loading(true)
            }.onCompletion {
                resultDetailUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultFollowing.value = Result.Success(it)
            }.collect{
                resultFollowing.value = Result.Success(it)
            }
        }
    }
}