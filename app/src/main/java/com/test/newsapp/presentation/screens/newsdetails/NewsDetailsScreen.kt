package com.test.newsapp.presentation.screens.newsdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.test.newsapp.R
import com.test.newsapp.presentation.models.NewsUI
import com.test.newsapp.presentation.screens.news.SharedViewModel

/**
 * ce composant présente
 * l'écran qui permet d'afficher
 * le détails de l'actualité
 */
@Composable
fun NewsDetailsScreen(
    navController: NavController,
    sharedViewModel: SharedViewModel,
    onClick: (String) -> Unit
) {
    val newsUI = sharedViewModel.selectedNews
    if (newsUI == null) {
        // si le newsUI est null on reviens vers l'écran précedant
        navController.popBackStack()
    } else {
        // LazyColumn permet d'afficher la liste des elements verticalement et scrollable
        LazyColumn(Modifier.fillMaxSize()) {
            item {
                //image de l'actualité
                AsyncImage(
                    model = newsUI.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                // le titre
                Text(
                    text = newsUI.title,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                // espace
                Spacer(Modifier.size(10.dp))
                // le label déscription de l'actualité
                Text(
                    text = stringResource(R.string.description),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )
                // espace
                Spacer(Modifier.size(10.dp))
                // la déscription de l'actualité
                Text(
                    text = newsUI.description,
                    color = Color.Black,
                    fontSize = 12.sp,
                )
                // espace
                Spacer(Modifier.size(10.dp))
                // le label contenu de l'actualité
                Text(
                    text = stringResource(R.string.content),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )
                // espace
                Spacer(Modifier.size(10.dp))
                // le contenu de l'actualité
                Text(
                    text = newsUI.content,
                    color = Color.Black,
                    fontSize = 12.sp,
                )
                Spacer(Modifier.size(10.dp))
                // label de date de publication
                Text(
                    text = stringResource(R.string.publish_at),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )
                // espace
                Spacer(Modifier.size(10.dp))
                // la date de publication
                Text(
                    text = newsUI.publishedAt ?: "",
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 1,
                )
                Spacer(Modifier.size(10.dp))
                // le label autheur
                Text(
                    text = stringResource(R.string.author),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                )
                // espace
                Spacer(Modifier.size(10.dp))

                // l'autheur
                Text(
                    text = newsUI.author,
                    color = Color.Black,
                    fontSize = 12.sp,
                    maxLines = 1,
                )
                // espace
                Spacer(Modifier.size(10.dp))

                //
                FloatingActionButton(
                    onClick = {
                        // ouvrir le lien sur le navigateur
                        onClick(newsUI.url)
                    }
                ) {
                    // icon de la bouton
                    Image(
                        painterResource(R.drawable.ic_website),
                        contentDescription = "",
                        alignment = Alignment.BottomEnd,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(30.dp)
                    )
                }
                // espace en bas
                Spacer(Modifier.size(10.dp))
            }
        }
    }
}

/**
 *  cette preview permet
 *  d'afficher l'écran détails
 *  sur l'éditeur
 */
@Preview
@Composable
fun NewsDetailsScreenPreview() {
    val sharedViewModel = SharedViewModel()
    sharedViewModel.selectedNews = NewsUI(
        author = "The White House",
        content = " Russia carried out a horrific aerial attack against Ukraine. Ukrainian authorities report that Russia launched nearly 200 missiles and drones against Ukrainian cities and energy …",
        description = " Russia carried out a horrific aerial attack against Ukraine. Ukrainian authorities report that Russia launched nearly 200 missiles and drones against Ukrainian cities and energy …",
        publishedAt = "2024-11-30 10:16:48",
        title = "Statement from President Joe Biden on Russia’s Attack on Ukraine - The White House",
        url = "https://www.whitehouse.gov/briefing-room/statements-releases/2024/11/28/statement-from-president-joe-biden-on-russias-attack-on-ukraine/",
        urlToImage = "https://www.whitehouse.gov/wp-content/uploads/2021/01/wh_social-share.png"
    )
    NewsDetailsScreen(navController = rememberNavController(), sharedViewModel = sharedViewModel) {}
}

