package com.andrii.booktracker.data

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.andrii.booktracker.data.local.AppDatabase
import com.andrii.booktracker.data.remote.BookApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(
    context: Context
) {
    private val database =
        Room.databaseBuilder<AppDatabase>(
            context.applicationContext,
            "book_tracker.db"
        )
            .setDriver(
                AndroidSQLiteDriver()
            )
            .build()

    private val json = Json {
        ignoreUnknownKeys = true
    }

    companion object {
        private const val BASE_URL = "https://gutendex1.com"
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    private val bookApiService = retrofit.create(BookApiService::class.java)

    val bookRepository =
        BookRepository(
            bookDao = database.bookDao(),
            bookApiService = bookApiService
        )
}