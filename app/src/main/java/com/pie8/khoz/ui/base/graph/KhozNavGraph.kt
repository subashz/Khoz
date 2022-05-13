package com.pie8.khoz.ui.base.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pie8.khoz.data.posts.impl.FakePostsRepository
import com.pie8.khoz.ui.features.home.HomeRoute
import com.pie8.khoz.ui.features.home.HomeViewModel

@Composable
fun KhozNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = KhozDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(KhozDestinations.HOME_ROUTE) {
            val homeViewModel: HomeViewModel = HomeViewModel(FakePostsRepository())
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = openDrawer
            )
        }
        composable(KhozDestinations.IMAGE_PREVIEW) {
            val homeViewModel: HomeViewModel = HomeViewModel(FakePostsRepository())

//            InterestsRoute(
//                interestsViewModel = interestsViewModel,
//                isExpandedScreen = isExpandedScreen,
//                openDrawer = openDrawer
//            )
        }
    }
}
