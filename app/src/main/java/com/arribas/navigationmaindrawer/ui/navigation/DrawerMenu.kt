package com.arribas.navigationmaindrawer.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu

object DrawerMenu {
    fun getMenuData(): List<DrawerItem> {
        return listOf(
            DrawerItem(
                id = NavTag.HOME,
                icon = Icons.Default.Menu,
                title = "Inicio",
                contentDescription = "64",
                menuLeftVisible = true,
                menuBottomVisible = true
            ),
            DrawerItem(
                id = NavTag.USER,
                icon = Icons.Default.List,
                title = "Usuario",
                contentDescription = "12",
                menuLeftVisible = true,
                menuBottomVisible = true
            ),
            DrawerItem(
                id = NavTag.FAVORITE,
                icon = Icons.Default.Favorite,
                title = "Favoritos",
                contentDescription = "25",
                menuLeftVisible = true,
                menuBottomVisible = true
            ),
            DrawerItem(
                id = NavTag.INTRA,
                icon = Icons.Default.AccountBox,
                title = "Intra",
                contentDescription = "25"
            ),
        )
    }
}