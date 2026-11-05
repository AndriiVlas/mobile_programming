package com.andrii.booktracker.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BooksResponseDto(
    val results: List<BookDto>
)