package com.andrii.booktracker.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object BookListRoute : NavKey

@Serializable
data object AddBookRoute : NavKey

@Serializable
data class BookDetailsRoute(
    val bookId: Int
) : NavKey
