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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update


class BookViewModel (private val repository: BookRepository) : ViewModel() {
    private data class RefreshUiState(
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    private val _refreshState = MutableStateFlow(RefreshUiState())

    val uiState: StateFlow<BookListUiState> = combine(
        repository.observeBooks(),
        _refreshState
    ) { books, refreshState ->
        BookListUiState(
            books = books,
            isLoading = refreshState.isLoading,
            errorMessage = refreshState.errorMessage
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
                    val repository = application.container.bookRepository

                    BookViewModel(
                        repository = repository
                    )
                }
            }
    }

    fun refreshBooks() {
        viewModelScope.launch {
            _refreshState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            try {
                val result = repository.refreshBooks()
                if (!result.isSuccess) {
                    _refreshState.update { it.copy(errorMessage = result.errorMessage) }
                }
            } finally {
                _refreshState.update { it.copy(isLoading = false) }
            }
        }
    }
}

