package com.test.newsapp.data.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object Utils {
    const val FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun convertStringToLocalDateTime(dateString: String?): LocalDateTime? {
        if (dateString.isNullOrBlank()) {
            return null
        }
        return try {
            val formatter = DateTimeFormatter.ofPattern(FORMAT_DATE, Locale.ENGLISH)
            LocalDateTime.parse(dateString, formatter)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}