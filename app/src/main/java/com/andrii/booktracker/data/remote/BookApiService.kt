package com.andrii.booktracker.data.remote

import com.andrii.booktracker.data.remote.dto.BooksResponseDto
import retrofit2.http.GET

interface BookApiService {
    @GET ("books/")
    suspend fun getBooks(): BooksResponseDto
}