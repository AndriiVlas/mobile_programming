package com.andrii.booktracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrii.booktracker.ui.theme.BookTrackerTheme

import com.andrii.booktracker.ui.screens.BookList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookTrackerTheme {
                BookTrackerApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookTrackerPreview() {
    BookList(
        books = listOf(),
        modifier = Modifier.padding(8.dp)
    )
}