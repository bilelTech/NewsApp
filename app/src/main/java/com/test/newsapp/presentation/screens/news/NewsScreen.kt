package com.test.newsapp.presentation.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.test.newsapp.presentation.models.NewsUI
import coil.compose.AsyncImage
import com.test.newsapp.R
import com.test.newsapp.utils.network.NoNetworkException

/**
 * ce composant présente l'écran d'actualités
 */
@Composable
fun NewsScreen(sharedViewModel: SharedViewModel, navController: NavController) {

    val newsViewModel: NewsViewModel = hiltViewModel()
    val newsPagingItems: LazyPagingItems<NewsUI> =
        newsViewModel.newsState.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when {
            newsPagingItems.itemCount > 0 -> {
                LazyColumn {
                    items(newsPagingItems.itemCount) { index ->
                        newsPagingItems[index]?.let { newsItem ->
                            NewsItem(news = newsItem, onClick = {
                                sharedViewModel.selectedNews = newsItem
                                navController.navigate("Details")
                            })
                        }
                    }
                    if (newsPagingItems.loadState.refresh is LoadState.Loading) {
                        item {
                            CircularProgressIndicator()
                        }

                    }
                }
            }
            newsPagingItems.loadState.refresh is LoadState.Loading -> {
                // Loading full screen
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primaryContainer)
                }
            }
            newsPagingItems.loadState.refresh is LoadState.Error -> {
                val errorMsg =
                    if ((newsPagingItems.loadState.refresh as LoadState.Error).error is NoNetworkException) {
                        stringResource(R.string.error_network)
                    } else {
                        stringResource(R.string.error_server)
                    }
                // ERROR
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = errorMsg, textAlign = TextAlign.Center)
                    Button(onClick = { newsViewModel.getNews() }) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
            }
        }
    }
}

/**
 * ce composant presente chaque element de la
 * liste des actualités
 */
@Composable
fun NewsItem(modifier: Modifier = Modifier, news: NewsUI, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = news.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                text = news.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Text(
                text = news.publishedAt ?: "",
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
            Text(
                text = news.author,
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
    }
}

/**
 * presentation de
 * l'element de la liste des actualité
 */
@Preview
@Composable
fun NewsItemPreview(modifier: Modifier = Modifier) {
    NewsItem(
        modifier,
        NewsUI(
            author = "The White House",
            content = "Overnight, Russia carried out a horrific aerial attack against Ukraine. Ukrainian authorities report that Russia launched nearly 200 missiles and drones against Ukrainian cities and energy …",
            description = " Russia carried out a horrific aerial attack against Ukraine. Ukrainian authorities report that Russia launched nearly 200 missiles and drones against Ukrainian cities and energy …",
            publishedAt = "2024-11-30 10:16:48",
            title = "Statement from President Joe Biden on Russia’s Attack on Ukraine - The White House",
            url = "https://www.whitehouse.gov/briefing-room/statements-releases/2024/11/28/statement-from-president-joe-biden-on-russias-attack-on-ukraine/",
            urlToImage = "https://www.whitehouse.gov/wp-content/uploads/2021/01/wh_social-share.png"
        )
    ) {}
}