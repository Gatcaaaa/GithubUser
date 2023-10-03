package com.project.githubuser.database

import android.content.Context
import androidx.room.Room

class DatabaseConfig(private val context: Context) {
    private val database = Room.databaseBuilder(context, FavoriteDatabase::class.java, "Favorite.db")
        .allowMainThreadQueries()
        .build()

    val favoriteDao = database.favoriteDao()
}