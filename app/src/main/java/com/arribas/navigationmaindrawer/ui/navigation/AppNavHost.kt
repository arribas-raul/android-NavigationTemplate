package com.arribas.navigationmaindrawer.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.arribas.navigationmaindrawer.ui.view.ScreenFavorite
import com.arribas.navigationmaindrawer.ui.view.ScreenHome
import com.arribas.navigationmaindrawer.ui.view.ScreenIntra
import com.arribas.navigationmaindrawer.ui.view.ScreenUser

@Composable
fun AppNavHost(
    navController: NavHostController,
    onItemClick: (NavTag) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.ScreenHome.route,
        modifier = modifier
    ) {
        composable(
            route = Routes.ScreenHome.route
        ){
            ScreenHome(
                onClick = { onItemClick(NavTag.INTRA) }
            )
        }

        composable(
            route = Routes.ScreenUser.route
        ){
            ScreenUser()
        }

        composable(
            route = Routes.ScreenFavorite.route
        ){
            ScreenFavorite()
        }

        composable(
            route = Routes.ScreenIntra.route
        ){
            ScreenIntra()
        }
    }
}