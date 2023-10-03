package com.project.githubuser.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.githubuser.data.response.UserResponse

@Database(entities = [UserResponse.ItemsItem::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase:RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao
}