package com.andrii.booktracker.data

data class RefreshResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)