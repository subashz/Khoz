package com.barrie.khoz.ui.base.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.barrie.khoz.data.posts.PostsRepository
import com.barrie.khoz.data.posts.impl.FakePostsRepository
import com.barrie.khoz.ui.features.home.HomeRoute
import com.barrie.khoz.ui.features.home.HomeViewModel

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
