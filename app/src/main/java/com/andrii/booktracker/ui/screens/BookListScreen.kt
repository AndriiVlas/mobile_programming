package com.andrii.booktracker.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.andrii.booktracker.model.Book
import com.andrii.booktracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    onBookClick: (Int) -> Unit,
    onAddBook: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.books_title))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBook
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        BookList(
            books = books,
            onBookClick = onBookClick,
            modifier = Modifier.padding(
                innerPadding
            )
        )
    }
}