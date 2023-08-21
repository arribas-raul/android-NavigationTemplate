package com.arribas.navigationmaindrawer.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arribas.navigationmaindrawer.ui.view.MyBottomAppBar
import com.arribas.navigationmaindrawer.ui.view.MyTopAppBar
import com.arribas.navigationmaindrawer.ui.view.ScreenFavorite
import com.arribas.navigationmaindrawer.ui.view.ScreenHome
import com.arribas.navigationmaindrawer.ui.view.ScreenIntra
import com.arribas.navigationmaindrawer.ui.view.ScreenUser
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()

    val items = listOf(
        DrawerItem(id = NavTag.HOME, icon = Icons.Default.Menu, title = "Inicio", contentDescription = "64"),
        DrawerItem(id = NavTag.USER, icon = Icons.Default.List, title = "Usuario", contentDescription = "12"),
        DrawerItem(id = NavTag.FAVORITE, icon = Icons.Default.Favorite, title = "Favoritos", contentDescription = "25"),
        DrawerItem(id = NavTag.INTRA, icon = Icons.Default.AccountBox, title = "Intra", contentDescription = "25"),
    )

    var lastSelectedItem by remember { mutableStateOf(items[0]) }
    var selectedItem by remember { mutableStateOf(items[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet{
                DrawerHeader()

                DrawerBody(
                    items = items,
                    selectedItem = selectedItem,
                    onItemClick = {
                        selectedItem = it
                        scope.launch { drawerState.close() }

                        when(it.id){
                            NavTag.HOME -> {
                                navController.navigate(Routes.ScreenHome.route)
                            }

                            NavTag.USER -> {
                                navController.navigate(Routes.ScreenUser.route)
                            }

                            NavTag.FAVORITE -> {
                                navController.navigate(Routes.ScreenFavorite.route)
                            }

                            NavTag.INTRA -> {
                                navController.navigate(Routes.ScreenIntra.route)
                            }
                        }
                    }
                )
            }
        },
        content = {
            Content(
                title = selectedItem.title,
                items = items,
                navController = navController,
                selectedItem = selectedItem,
                onClick = { scope.launch{ drawerState.open() } },

                onItemClick = {

                    scope.launch { drawerState.close() }

                    when(it){
                        NavTag.HOME -> {
                            selectedItem = items[0]
                            navController.navigate(Routes.ScreenHome.route)
                        }

                        NavTag.USER -> {
                            selectedItem = items[1]
                            navController.navigate(Routes.ScreenUser.route)
                        }

                        NavTag.FAVORITE -> {
                            selectedItem = items[2]
                            navController.navigate(Routes.ScreenFavorite.route)
                        }

                        NavTag.INTRA -> {
                            lastSelectedItem = selectedItem
                            selectedItem = items[3]
                            navController.navigate(Routes.ScreenIntra.route)
                        }
                    }
                },

                navigateUp = {
                    selectedItem = lastSelectedItem
                    navController.popBackStack()
                }
            )
        }
    )
}

@Composable
fun DrawerHeader(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 64.dp)
    ){
        Text(
            text = "Cabecera",
            fontSize = 24.sp,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBody(
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit,
    selectedItem: DrawerItem
){
    items.forEach { item ->
        if(item.id == NavTag.INTRA){
            return
        }

        NavigationDrawerItem(
            label = { Text( text = item.title ) },
            selected = item == selectedItem,
            onClick = { onItemClick(item) },
            icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
            badge = { Text(text = item.contentDescription)},
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    title: String,
    navController: NavHostController,
    items: List<DrawerItem>,
    selectedItem: DrawerItem,
    onItemClick: (NavTag) -> Unit,
    onClick: () -> Unit,
    navigateUp: () -> Unit,
){
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = title,
                canNavigateBack = selectedItem.id == NavTag.INTRA,
                navigateUp = navigateUp,
                onClickDrawer = onClick
            )
        },

        bottomBar = {
            MyBottomAppBar(
                currentTab =  selectedItem.id,
                onTabPressed = { onItemClick(it) },
                navigationItemContentList = items
            )
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = Routes.ScreenHome.route,
            modifier = Modifier.padding(padding)
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
}