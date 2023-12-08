package com.shobhit97.sample.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shobhit97.sample.data.local.dao.UserDao
import com.shobhit97.sample.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }
}