package com.project.githubuser.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import com.project.githubuser.ui.utils.Result

class mainViewModel: ViewModel() {

    val resultUser = MutableLiveData<Result>()

    fun getUser(){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getUser()

                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect{
                resultUser.value = Result.Success(it)
            }
        }
    }

    fun getUser(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiConfig
                    .gitHubService
                    .getSearchUser(mapOf(
                        "q" to username,
                        "per_page" to 10

                    ))
                emit(response)
            }.onStart {
                resultUser.value = Result.Loading(true)
            }.onCompletion {
                resultUser.value = Result.Loading(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultUser.value = Result.Error(it)
            }.collect{
                resultUser.value = Result.Success(it.items)
            }
        }
    }
}