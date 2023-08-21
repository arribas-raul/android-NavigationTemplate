package com.arribas.navigationmaindrawer.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
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
import androidx.navigation.compose.rememberNavController
import com.arribas.navigationmaindrawer.ui.view.MyBottomAppBar
import com.arribas.navigationmaindrawer.ui.view.MyTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val items = DrawerMenu.getMenuData()

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
                        onSelectItemNavDrawer(
                            selectedItem, drawerState, scope, navController)
                    }
                )
            }
        }
    ) {
        Content(
            title = selectedItem.title,
            items = items,
            navController = navController,
            selectedItem = selectedItem,
            onClick = { scope.launch { drawerState.open() } },

            onItemClick = {
                val navTag: NavTag = it

                lastSelectedItem = selectedItem
                selectedItem = items.find { item -> item.id == navTag }!!

                onSelectItemNavDrawer(
                    selectedItem, drawerState, scope, navController)
            },

            navigateUp = {
                selectedItem = lastSelectedItem
                navController.popBackStack()
            }
        )
    }
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
        if(!item.menuLeftVisible){
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

        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(padding),
            onItemClick = { onItemClick(it) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun onSelectItemNavDrawer(
    selectedItem: DrawerItem,
    drawerState: DrawerState,
    scope: CoroutineScope,
    navController: NavHostController
){
    scope.launch { drawerState.close() }

    when(selectedItem.id){
        NavTag.HOME     -> navController.navigate(Routes.ScreenHome.route)
        NavTag.USER     -> navController.navigate(Routes.ScreenUser.route)
        NavTag.FAVORITE -> navController.navigate(Routes.ScreenFavorite.route)
        NavTag.INTRA    -> navController.navigate(Routes.ScreenIntra.route)
    }
}