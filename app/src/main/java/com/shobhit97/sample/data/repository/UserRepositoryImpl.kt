package com.shobhit97.sample.data.repository

import com.shobhit97.sample.data.local.dao.UserDao
import com.shobhit97.sample.data.remote.UserApi
import com.shobhit97.sample.domain.model.User
import com.shobhit97.sample.domain.model.Users
import com.shobhit97.sample.domain.repository.UserRepository
import com.shobhit97.sample.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.net.UnknownHostException

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val userDao: UserDao
) : UserRepository {

    override fun getUsers(): Flow<Resource<Users>> = flow {
        emit(Resource.Loading())
        try {
            val result = userApi.getUsers()
            if (result.isSuccessful) {
                emit(Resource.Success(result.body()))
            } else {
                emit(Resource.Error(result.errorBody().toString()))
            }
        } catch (ex: UnknownHostException) {
            emit(Resource.Error(ex.localizedMessage ?: "Unknown Host Exception"))

        } catch (ex: Exception) {
            emit(Resource.Error(ex.localizedMessage ?: "Something Went Wrong"))
        }

    }.flowOn(Dispatchers.IO)

    override fun getFavUsers(): Flow<List<User>> {
        return userDao.getFavUsers()
    }

    override suspend fun addFavUser(user: User) {
        userDao.addFavUser(user)
    }

    override suspend fun removeFavUser(user: User) {
        userDao.removeFavUser(user)
    }
}