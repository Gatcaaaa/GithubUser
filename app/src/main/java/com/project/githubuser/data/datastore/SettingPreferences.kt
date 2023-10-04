package com.project.githubuser.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.prefDataStore by preferencesDataStore("Setting")

class SettingPreferences constructor(context: Context){

    private val settingDataStore = context.prefDataStore
    private val themeKEY = booleanPreferencesKey("theme_setting")

    fun getTheme(): Flow<Boolean> =
        settingDataStore.data.map { pref ->
            pref[themeKEY]?: false
        }

    suspend fun getSaveTheme(
        isNightMode: Boolean
    ){
        settingDataStore.edit { pref ->
            pref[themeKEY] = isNightMode
        }
    }
}