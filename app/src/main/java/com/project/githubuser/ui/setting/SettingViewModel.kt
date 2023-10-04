package com.project.githubuser.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.githubuser.data.datastore.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref : SettingPreferences): ViewModel() {

    class Factory(private val pref: SettingPreferences):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T  = SettingViewModel(pref) as T
    }

    fun getTheme() = pref.getTheme().asLiveData()

    fun saveTheme(isNightMode: Boolean){
        viewModelScope.launch {
            pref.getSaveTheme(isNightMode)
        }
    }
}