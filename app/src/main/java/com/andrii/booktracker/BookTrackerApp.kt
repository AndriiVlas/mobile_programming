package com.andrii.booktracker

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.andrii.booktracker.model.Book
import com.andrii.booktracker.navigation.AddBookRoute
import com.andrii.booktracker.navigation.BookListRoute
import com.andrii.booktracker.ui.screens.AddBookScreen
import com.andrii.booktracker.ui.screens.BookList
import com.andrii.booktracker.ui.screens.BookListScreen
import com.andrii.booktracker.navigation.BookDetailsRoute
import com.andrii.booktracker.ui.screens.BookDetailsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookTrackerApp() {
    var books by remember {
        mutableStateOf(
            listOf(
                Book(
                    id = 1,
                    title = "Clean Code",
                    author = "Robert C. Martin",
                    description = "A handbook of agile software craftsmanship",
                    isRead = true
                ),
                Book(
                    id = 2,
                    title = "Kotlin in Action",
                    author = "Dmitry Jemerov, Svetlana Isakova",
                    description = null,
                    isRead = false
                ),
                Book(
                    id = 3,
                    title = "Refactoring",
                    author = "Martin Flower",
                    description = "Improving the design of existing code.",
                    isRead = false
                )
            )
        )
    }

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
                    onCancel = {
                        backStack.removeLastOrNull()
                    },
                    onAddBook = {
                        title,
                        author,
                        description,
                        isRead ->
                        val nextId = (books.maxOfOrNull {
                            it.id
                        } ?: 0) + 1
                        books = books + Book(
                            id = nextId,
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
                    onBack = {
                        backStack.removeLastOrNull()
                    },
                    onToggleRead = {
                        books = toggleBookRead(
                            books = books,
                            bookId = route.bookId
                        )
                    }
                )
            }
        }
    )
}

private fun toggleBookRead(
    books: List<Book>,
    bookId: Int
): List<Book> {
    return books.map {
            book -> if (book.id == bookId) {
        book.copy(
            isRead = !book.isRead
        )
    } else {
        book
    }
    }
}