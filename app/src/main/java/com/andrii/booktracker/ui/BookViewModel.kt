package com.andrii.booktracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.andrii.booktracker.BookTrackerApplication
import com.andrii.booktracker.data.BookRepository
import com.andrii.booktracker.model.Book
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY


class BookViewModel (private val repository: BookRepository) : ViewModel() {
    val uiState: StateFlow<BookListUiState> =
        repository
            .observeBooks()
            .map { books ->
                BookListUiState(
                    books = books
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookListUiState()
            )

    fun addBook(
        title: String,
        author: String,
        description: String?,
        isRead: Boolean
    ) {

        val book = Book(
            id = 0,
            title = title,
            author = author,
            description = description,
            isRead = isRead
        )

        viewModelScope.launch {
            repository.addBook(
                book
            )
        }
    }

    fun toggleBookRead(
        bookId: Int
    ) {
        val book = uiState
            .value
            .books
            .find { it.id == bookId } ?: return

        val updatedBook = book.copy(
            isRead = !book.isRead
        )

        viewModelScope.launch {
            repository.updateBook(
                updatedBook
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = this[APPLICATION_KEY] as BookTrackerApplication
                    val reository = application.container.bookRepository

                    BookViewModel(
                        repository = reository
                    )
                }
            }
    }
}

