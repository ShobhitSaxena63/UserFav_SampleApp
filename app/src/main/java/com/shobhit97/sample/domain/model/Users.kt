package com.shobhit97.sample.domain.model


data class Users(
    val `data`: List<User>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)