package com.andrii.booktracker.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andrii.booktracker.R

@Composable
fun AddBookForm(
    title: String,
    author: String,
    description: String,
    isRead: Boolean,

    onTitleChange: (String) -> Unit,
    onAuthorChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onReadChange: (Boolean) -> Unit,

    onSave: () -> Unit,
    onCancel: () -> Unit,

    modifier: Modifier = Modifier
) {
    val isFormValid = title.isNotBlank() && author.isNotBlank()

    Column(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(
                    stringResource(R.string.book_title)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = author,
            onValueChange = onAuthorChange,
            label = {
                Text(
                    stringResource(R.string.book_author)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = {
                Text(
                    stringResource(R.string.book_description)
                )
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 4
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.book_is_read)
            )

            Checkbox(
                checked = isRead,
                onCheckedChange = onReadChange
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onCancel
            ) {
                Text(
                    stringResource(R.string.cancel)
                )
            }

            Button(
                onClick = onSave,
                enabled = isFormValid
            ) {
                Text(
                    stringResource(R.string.save)
                )
            }
        }
    }
}