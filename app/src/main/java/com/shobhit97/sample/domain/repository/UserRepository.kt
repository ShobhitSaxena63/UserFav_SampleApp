package com.shobhit97.sample.domain.repository

import com.shobhit97.sample.domain.model.User
import com.shobhit97.sample.domain.model.Users
import com.shobhit97.sample.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUsers(): Flow<Resource<Users>>

    fun getFavUsers():Flow<List<User>>

    suspend fun addFavUser(user: User)

    suspend fun removeFavUser(user: User)
}