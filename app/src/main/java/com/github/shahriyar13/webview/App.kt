package com.github.shahriyar13.webview

import android.app.Application

class App: Application() {

    companion object {
        private lateinit var context: App

        fun getContext() = context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}