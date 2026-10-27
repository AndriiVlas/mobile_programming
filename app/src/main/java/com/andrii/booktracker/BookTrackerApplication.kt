package com.andrii.booktracker

import android.app.Application
import com.andrii.booktracker.data.AppContainer

class BookTrackerApplication : Application() {
    lateinit var container : AppContainer
    override fun onCreate() {
        super.onCreate()

        container = AppContainer(applicationContext)
    }
}