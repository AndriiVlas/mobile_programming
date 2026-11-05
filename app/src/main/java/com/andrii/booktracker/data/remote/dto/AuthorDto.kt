package com.andrii.booktracker.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorDto(
    val name: String
)