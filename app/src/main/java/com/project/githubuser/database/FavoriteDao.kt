package com.project.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.githubuser.data.response.UserResponse


@Dao
interface FavoriteDao {


    @Query("SELECT * FROM User")
    fun loads(): LiveData<MutableList<UserResponse.ItemsItem>>

    @Query("SELECT * FROM User Where id LIKE :id LIMIT 1")
    fun findById(id: Int): UserResponse.ItemsItem

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserResponse.ItemsItem)

    @Query("SELECT COUNT(*) FROM User Where id = :id")
    fun userExist(id: Int): Int

    @Delete
    fun delete(user: UserResponse.ItemsItem)

}