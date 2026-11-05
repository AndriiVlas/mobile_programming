package com.andrii.booktracker.data

import androidx.lifecycle.viewModelScope
import com.andrii.booktracker.data.local.BookDao
import com.andrii.booktracker.data.remote.BookApiService
import com.andrii.booktracker.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException

class BookRepository(
    private val bookDao: BookDao,
    private val bookApiService: BookApiService
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

    suspend fun refreshBooks(): RefreshResult {
        return try {
            val response = bookApiService.getBooks()
            val entities = response.results.map { it.toBook().toEntity() }
            bookDao.upsertAll(entities)

            RefreshResult(isSuccess = true)
        } catch (e: IOException) {
            RefreshResult(isSuccess = false, errorMessage = "Немає з'єднання з сервером")
        } catch (e: HttpException) {
            RefreshResult(isSuccess = false, errorMessage = "Помилка сервера: ${e.code()}")
        } catch (e: SerializationException) {
            RefreshResult(isSuccess = false, errorMessage = "Некоректна відповідь сервера")
        }
    }
}