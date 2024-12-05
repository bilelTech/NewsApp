package com.test.newsapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.test.newsapp.R
import com.test.newsapp.presentation.screens.news.NewsScreen
import com.test.newsapp.presentation.screens.news.SharedViewModel
import com.test.newsapp.presentation.screens.newsdetails.NewsDetailsScreen
import com.test.newsapp.presentation.ui.theme.NewsAppTheme
import com.test.newsapp.presentation.utils.Constants
import com.test.newsapp.presentation.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * variables
     */
    private val sharedViewModel: SharedViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val backStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntry.value?.destination?.route
            val showBackButton = currentDestination != Constants.ROOT_NEWS
            // affichage de titre selon l'écran affiché
            val title =
                if (currentDestination == Constants.ROOT_NEWS) this.getString(R.string.news_title) else this.getString(
                    R.string.details
                )
            NewsAppTheme {
                Scaffold(topBar = {
                    // la toolbar en haut
                    TopAppBar(colors = topAppBarColors(
                        // le couleur de contenu de toolbar
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        // le couleur de titre de Toolbar
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ), navigationIcon = {
                        // controle sur l'afficage de bouton retour
                        if (showBackButton) {
                            // affichage de boutton
                            IconButton(onClick = {
                                // reviens vers l'écran précedant
                                navController.popBackStack()
                            }) {
                                // affiche de la bouton retour
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // l'icon de bouton retour
                                    contentDescription = stringResource(R.string.back), // description
                                    tint = MaterialTheme.colorScheme.primary // le couleur de bouton
                                )
                            }
                        }
                    }, title = {
                        // le titre de toolbar
                        Text(
                            title, fontWeight = FontWeight.Bold
                        )
                    })
                }) { innerPadding ->
                    // afficher les élement sous forme verticale
                    Column(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        // le systeme de navigation
                        NavHost(
                            navController = navController, startDestination = Constants.ROOT_NEWS
                        ) {
                            composable(Constants.ROOT_NEWS) {
                                // ouvrir l'écran des actualités
                                NewsScreen(sharedViewModel, navController)
                            }
                            composable(Constants.ROOT_DETAIL) {
                                NewsDetailsScreen(navController, sharedViewModel, onClick = { url ->
                                    //ouvrir l'url sur le navigateur
                                    val browserIntent = Intent(
                                        Intent.ACTION_VIEW, Uri.parse(url)
                                    )
                                    startActivity(browserIntent)
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // mettre à jour la language de l'application
        Utils.updateLocale(this, Locale.getDefault().language)
    }
}
