package com.shobhit97.sample.presentation.users_list

import com.shobhit97.sample.domain.model.Users


data class UserState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val users: Users? = null,
)