package com.andrii.booktracker.data

import com.andrii.booktracker.data.local.BookEntity
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