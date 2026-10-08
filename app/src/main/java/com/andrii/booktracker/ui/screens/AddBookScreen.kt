package com.andrii.booktracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextButton
import androidx.compose.material3.Text
import com.andrii.booktracker.model.Book

@Composable
fun AddBookScreen (
    onAddBook: (
        String,
        String,
        String?,
        Boolean,
    ) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }

    var author by rememberSaveable {
        mutableStateOf("")
    }

    var description by rememberSaveable {
        mutableStateOf("")
    }

    var isRead by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement
            .spacedBy(8.dp)) {

        TextButton(
            onClick = {
                title = ""
                author = ""
                description = ""
                isRead = false
            }
        ) {
            Text("Очистити форму")
        }

        AddBookForm(
            title = title,
            author = author,
            description = description,
            isRead = isRead,

            onTitleChange = {
                title = it
            },

            onAuthorChange = {
                author = it
            },

            onDescriptionChange = {
                description = it
            },

            onReadChange = {
                isRead = it
            },

            onSave = {
                onAddBook(
                    title.trim(),
                    author.trim(),
                    description.trim().takeIf{it.isNotEmpty()},
                    isRead
                )
            },

            onCancel = onCancel,
            modifier = modifier
        )
    }
}