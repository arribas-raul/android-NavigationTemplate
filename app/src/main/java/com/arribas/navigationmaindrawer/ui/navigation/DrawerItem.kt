package com.arribas.navigationmaindrawer.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val id: NavTag,
    val title: String,
    val contentDescription: String,
    val icon: ImageVector,
    val menuLeftVisible: Boolean = false,
    val menuBottomVisible: Boolean = false
)
