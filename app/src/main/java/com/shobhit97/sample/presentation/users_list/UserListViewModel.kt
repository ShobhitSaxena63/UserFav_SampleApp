package com.shobhit97.sample.presentation.users_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shobhit97.sample.domain.model.User
import com.shobhit97.sample.domain.repository.UserRepository
import com.shobhit97.sample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UserState())
    var state: StateFlow<UserState> = _state.asStateFlow()

    fun getUsers() {
        userRepository.getUsers().onEach {
            when (it) {
                is Resource.Loading -> {
                    _state.value = UserState(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    _state.value = UserState(
                        users = it.data
                    )
                }

                is Resource.Error -> {
                    _state.value = UserState(
                        error = it.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFavUser(user: User) {
        viewModelScope.launch {
            userRepository.addFavUser(user)
        }
    }

    fun removeFavUser(user: User) {
        viewModelScope.launch {
            userRepository.removeFavUser(user)
        }
    }



}