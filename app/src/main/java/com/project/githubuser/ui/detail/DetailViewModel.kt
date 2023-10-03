package com.project.githubuser.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.project.githubuser.data.response.UserResponse
import com.project.githubuser.data.retrofit.ApiConfig
import com.project.githubuser.database.DatabaseConfig
import com.project.githubuser.ui.utils.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val database: DatabaseConfig): ViewModel() {
   //isUserInDatabase
    val userInDatabase: LiveData<Boolean> = MutableLiveData()
    //favDel
    val DeleteFav = MutableLiveData<Boolean>()
    //favSucces
    val isSuccessAddFav = MutableLiveData<Boolean>()

    val resultDetailUser = MutableLiveData<Result>()
    val resultFollowers = MutableLiveData<Result>()
    val resultFollowing = MutableLiveData<Result>()

    private var isFavorite = false

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

    /*
    setFav
     */
    fun setButtonFav(item: UserResponse.ItemsItem?){
        viewModelScope.launch {
            item?.let {
                if (isFavorite){
                    DeleteFav.value = false
                    database.favoriteDao.delete(item)
                } else {
                    isSuccessAddFav.value = true
                    database.favoriteDao.insert(item)
                }
            }
            isFavorite = !isFavorite
        }
    }

    /*
    findFav
     */
    fun checkFavorite(id: Int, listenFav: () -> Unit){
        viewModelScope.launch {
            val favoriteUser = database.favoriteDao.findById(id)
            if (favoriteUser == null){
                listenFav()
                isFavorite = true
            }
        }
    }

    /*
    checkUserInDatabase
     */
    fun checkingUser(userId:Int){
        viewModelScope.launch {
            val count = database.favoriteDao.userExist(userId)
            (userInDatabase as MutableLiveData).postValue(count > 0)
        }
    }

    class Factory(private val db: DatabaseConfig): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T  = DetailViewModel(db) as T

    }
}