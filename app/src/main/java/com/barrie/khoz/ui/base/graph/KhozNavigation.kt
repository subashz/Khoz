package com.barrie.khoz.ui.base.graph

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

/**
 * Destinations used in the [KhozApp].
 */
object KhozDestinations {
    const val HOME_ROUTE = "home"
    const val IMAGE_PREVIEW = "image"
}

/**
 * Models the navigation actions in the app.
 */
class KhozNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(KhozDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    val navigateToInterests: () -> Unit = {
        navController.navigate(KhozDestinations.IMAGE_PREVIEW) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}
