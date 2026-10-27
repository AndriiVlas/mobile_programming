package com.andrii.booktracker.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query(
        "SELECT * FROM books " +
        "ORDER BY id DESC"
    )
    fun observeAll():
            Flow<List<BookEntity>>
    @Insert
    suspend fun insert(
        book: BookEntity
    )
    @Update
    suspend fun update(
        book: BookEntity
    )
}