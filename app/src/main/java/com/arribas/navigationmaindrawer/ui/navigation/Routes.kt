package com.arribas.navigationmaindrawer.ui.navigation

sealed class Routes(val route: String){
    object ScreenHome: Routes("inicio")
    object ScreenUser: Routes("usuario")
    object ScreenFavorite: Routes("favorito")
    object ScreenIntra: Routes ("Intra")
}
