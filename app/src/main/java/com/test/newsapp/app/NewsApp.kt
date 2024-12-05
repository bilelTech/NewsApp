package com.test.newsapp.app

import android.app.Application
import com.test.newsapp.presentation.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class NewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.updateLocale(this, Locale.getDefault().language)
    }
}