package com.project.githubuser.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.githubuser.database.DatabaseConfig

class FavoriteViewModel(private val databaseConfig: DatabaseConfig):ViewModel() {
    class  factory(private val databaseConfig: DatabaseConfig): ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(databaseConfig) as T
    }

    fun getFavoriteUser() = databaseConfig.favoriteDao.loads()
}