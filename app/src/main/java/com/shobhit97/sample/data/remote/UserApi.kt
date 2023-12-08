package com.shobhit97.sample.data.remote

import com.shobhit97.sample.domain.model.Users
import retrofit2.Response
import retrofit2.http.GET


interface UserApi {

    @GET("users?page=2")
    suspend fun getUsers(): Response<Users>

}