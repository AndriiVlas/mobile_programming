package com.andrii.booktracker.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.andrii.booktracker.navigation.AddBookRoute
import com.andrii.booktracker.navigation.BookListRoute
import com.andrii.booktracker.ui.screens.AddBookScreen
import com.andrii.booktracker.ui.screens.BookListScreen
import com.andrii.booktracker.navigation.BookDetailsRoute
import com.andrii.booktracker.ui.screens.BookDetailsScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTrackerApp(
    bookViewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)
) {
    val bookViewModel: BookViewModel = viewModel()

    val uiState by bookViewModel
        .uiState
        .collectAsStateWithLifecycle()

    val books = uiState.books

    val backStack =
        rememberNavBackStack(
            BookListRoute
        )

    NavDisplay(
        backStack = backStack,
        onBack = {
            backStack.removeLastOrNull()
        },
        entryProvider = entryProvider {
            entry<BookListRoute> {
                BookListScreen(
                    books = books,
                    onBookClick = { bookId -> backStack.add(BookDetailsRoute(bookId = bookId)) },
                    onAddBook = {
                        backStack.add(AddBookRoute)
                    }
                )
            }

            entry<AddBookRoute> {
                AddBookScreen(
                    onCancel = { backStack.removeLastOrNull() },
                    onAddBook = {
                        title,
                        author,
                        description,
                        isRead ->
                        bookViewModel.addBook(
                            title = title,
                            author = author,
                            description = description,
                            isRead = isRead
                        )
                        backStack.removeLastOrNull()
                    },
                )
            }

            entry<BookDetailsRoute> { route ->
                val book = books.find {
                    it.id == route.bookId
                }

                BookDetailsScreen(
                    book = book,
                    onBack = { backStack.removeLastOrNull() },
                    onToggleRead = {
                        bookViewModel.toggleBookRead(
                            route.bookId
                        )
                    }
                )
            }
        }
    )
}