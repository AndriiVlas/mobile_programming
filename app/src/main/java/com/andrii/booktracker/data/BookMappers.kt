package com.andrii.booktracker.data

import com.andrii.booktracker.data.local.BookEntity
import com.andrii.booktracker.data.remote.dto.BookDto
import com.andrii.booktracker.model.Book

fun BookEntity.toBook(): Book =
    Book(
        id = id,
        title = title,
        author = author,
        description = description,
        isRead = isRead
    )
fun Book.toEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        author = author,
        description = description,
        isRead = isRead
    )

fun BookDto.toBook(): Book {
    val author = authors
        .joinToString(separator = ", ") { it.name }
        .ifBlank { "unknown author" }

    return Book(
        id = id,
        title = title,
        author = author,
        description = summaries.firstOrNull(),
        isRead = false
    )
}