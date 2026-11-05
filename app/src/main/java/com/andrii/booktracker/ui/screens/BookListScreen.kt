package com.andrii.booktracker.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andrii.booktracker.model.Book
import com.andrii.booktracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    books: List<Book>,
    isLoading: Boolean,
    errorMessage: String?,
    onBookClick: (Int) -> Unit,
    onAddBook: () -> Unit,
    onRefresh: () -> Unit
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
        Column (
            modifier = Modifier.padding(innerPadding)
        ) {
            Button(
                onClick = onRefresh,
                enabled = !isLoading,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Оновити з мережі")
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                TextButton(
                    onClick = onRefresh,
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Text("Спробувати ще раз")
                }
            }

            BookList(
                books = books,
                onBookClick = onBookClick
            )
        }
    }
}