package com.shobhit97.sample.presentation.favorite_user_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shobhit97.sample.domain.model.User
import com.shobhit97.sample.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavUserViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel() {

    fun addFavUser(user: User) {
        viewModelScope.launch {
            userRepository.addFavUser(user)
        }
    }

    fun getFavUsers() : Flow<List<User>> {
        return userRepository.getFavUsers()
    }

    fun removeFavUser(user: User) {
        viewModelScope.launch {
            userRepository.removeFavUser(user)
        }
    }
}