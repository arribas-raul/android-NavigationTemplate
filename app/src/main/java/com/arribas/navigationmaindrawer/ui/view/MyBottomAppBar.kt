package com.arribas.navigationmaindrawer.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.arribas.navigationmaindrawer.R
import com.arribas.navigationmaindrawer.ui.navigation.DrawerItem
import com.arribas.navigationmaindrawer.ui.navigation.NavTag

@Composable
fun MyBottomAppBar(
    currentTab: NavTag,
    onTabPressed: ((NavTag) -> Unit) = {},
    navigationItemContentList: List<DrawerItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = colorResource(R.color.purple_200)
    ) {

        for (navItem in navigationItemContentList) {
            if(!navItem.menuBottomVisible){
                continue
            }

            NavigationBarItem(
                selected = currentTab == navItem.id,
                onClick = { onTabPressed(navItem.id) },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor= colorResource(R.color.black),
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor= colorResource(R.color.purple_200),
                    unselectedIconColor= MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary),

                icon = {
                    Icon(imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                },

                modifier = Modifier
                    .background(colorResource(R.color.purple_200))
                    .padding(0.dp)
            )
        }
    }
}