package com.shobhit97.sample.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shobhit97.sample.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavUser(user: User)

    @Query("SELECT * FROM user")
    fun getFavUsers(): Flow<List<User>>

    @Delete
    suspend fun removeFavUser(user: User)
}