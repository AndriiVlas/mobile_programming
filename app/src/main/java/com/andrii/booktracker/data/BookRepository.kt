package com.andrii.booktracker.data

import com.andrii.booktracker.data.local.BookDao
import com.andrii.booktracker.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository(
    private val bookDao: BookDao
) {
    fun observeBooks(): Flow<List<Book>> =
        bookDao
            .observeAll()
            .map { entities ->
                entities.map {
                    it.toBook()
                }
            }

    suspend fun addBook(
        book: Book
    ) {
        bookDao.insert(
            book.toEntity()
        )
    }

    suspend fun updateBook(
        book: Book
    ) {
        bookDao.update(
            book.toEntity()
        )
    }
}