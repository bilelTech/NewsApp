package com.test.newsapp.data.converter

import com.test.newsapp.data.converters.mapFromEntity
import com.test.newsapp.data.utils.Utils
import com.test.newsapp.utils.Utils.createFakeArticleDto
import com.test.newsapp.utils.Utils.createFakeNews
import com.test.newsapp.utils.Utils.createNullArticleDto
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDateTime

class MapperTest {

    @Test
    fun `test mapFromEntity maps ArticleDto to News correctly`() {
        // Données fictives pour ArticleDto
        val articleDto = createFakeArticleDto()

        // Résultat LocalDateTime attendu
        val expectedDateTime = Utils.convertStringToLocalDateTime(articleDto.publishedAt)

        // Objet attendues
        val expectedNews = createFakeNews(expectedDateTime)

        val actualNews = articleDto.mapFromEntity()

        // Affirmations
        assertEquals(expectedNews.author, actualNews.author)
        assertEquals(expectedNews.content, actualNews.content)
        assertEquals(expectedNews.description, actualNews.description)
        assertEquals(expectedNews.publishedAt, actualNews.publishedAt)
        assertEquals(expectedNews.title, actualNews.title)
        assertEquals(expectedNews.url, actualNews.url)
        assertEquals(expectedNews.urlToImage, actualNews.urlToImage)
    }

    @Test
    fun `test mapFromEntity handles null fields gracefully`() {
        // Données fictives pour ArticleDto avec des champs nuls
        val articleDto = createNullArticleDto()

        // Comportement attendu pour une entrée nulle dans publishedAt
        val expectedDateTime: LocalDateTime? =
            null // Adjust based on actual behavior of Utils.convertStringToLocalDateTime(null)

        // Actual mapping
        val actualNews = articleDto.mapFromEntity()

        //Assertions pour les valeurs par défaut
        assertEquals("", actualNews.author) // Ensures the `orEmpty` function works correctly
        assertEquals("", actualNews.content)
        assertEquals("", actualNews.description)
        assertEquals(
            expectedDateTime,
            actualNews.publishedAt
        )
        // Doit correspondre au comportement de la méthode utilitaire
        assertEquals("", actualNews.title)
        assertEquals("", actualNews.url)
        assertEquals("", actualNews.urlToImage)
    }
}