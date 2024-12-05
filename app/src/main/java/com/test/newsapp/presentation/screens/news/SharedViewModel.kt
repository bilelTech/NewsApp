package com.test.newsapp.presentation.screens.news

import androidx.lifecycle.ViewModel
import com.test.newsapp.presentation.models.NewsUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    var selectedNews: NewsUI? = null
}