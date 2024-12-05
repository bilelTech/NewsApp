package com.test.newsapp.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.newsapp.data.converters.mapFromListModel
import com.test.newsapp.data.remote.NewsApi
import com.test.newsapp.data.utils.Constants
import com.test.newsapp.domain.models.News
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale
import javax.inject.Inject

class NewsPagingSource @Inject constructor(
    private val api: NewsApi,
) : PagingSource<Int, News>() {
    companion object {
        private const val STARTING_KEY = 1
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val startKey = params.key ?: STARTING_KEY
        return try {
            // on a utilisé comme exemple de recherche par le mot Apple
            // mais on peut ajouter une zone de recherche dans le future et change ce parametre sera variable
            // selon la zone de text sasie
            val news = api.getNews("Apple", Locale.getDefault().language, startKey, Constants.MAX_PAGE_SIZE)
            val nextKey = if (news.articles.isNullOrEmpty()) null else startKey + 1
            LoadResult.Page(
                data = news.articles?.mapFromListModel() ?: emptyList(),
                prevKey = if (startKey == STARTING_KEY) null else startKey - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (httpException: HttpException) {
            return LoadResult.Error(httpException)
        }
    }
}