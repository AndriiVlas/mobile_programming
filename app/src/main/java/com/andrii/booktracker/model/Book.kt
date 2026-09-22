package com.andrii.booktracker.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val description: String?,
    val isRead: Boolean
)
