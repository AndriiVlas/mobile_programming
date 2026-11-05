package com.andrii.booktracker.ui

import com.andrii.booktracker.model.Book

data class BookListUiState (
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)