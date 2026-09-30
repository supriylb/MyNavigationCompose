package com.example.mynavigationcompose.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mynavigationcompose.R
import com.example.mynavigationcompose.ui.components.CustomTopAppBar
import com.example.mynavigationcompose.ui.navigation.CategoryRoute
import com.example.mynavigationcompose.ui.navigation.DetailCategoryRoute
import com.example.mynavigationcompose.ui.navigation.HomeRoute
import com.example.mynavigationcompose.ui.navigation.ProfileRoute
import com.example.mynavigationcompose.ui.screens.CategoryScreen
import com.example.mynavigationcompose.ui.screens.DetailCategoryScreen
import com.example.mynavigationcompose.ui.screens.HomeScreen
import com.example.mynavigationcompose.ui.screens.ProfileScreen
import com.example.mynavigationcompose.ui.theme.MyNavigationComposeTheme

@Composable
fun AppShell(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canBack = navController.previousBackStackEntry != null
    val destination = currentBackStackEntry?.destination

    val title = when {
        destination?.hasRoute<HomeRoute>() == true -> stringResource(R.string.app_name)
        destination?.hasRoute<CategoryRoute>() == true -> stringResource(R.string.title_category)
        destination?.hasRoute<ProfileRoute>() == true -> stringResource(R.string.title_profile)
        destination?.hasRoute<DetailCategoryRoute>() == true -> {
            val detail = currentBackStackEntry?.toRoute<DetailCategoryRoute>()
            detail?.name ?: stringResource(R.string.title_category_default)
        }
        else -> stringResource(R.string.app_name)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = title,
                canBack = canBack,
                onBack = navController::navigateUp,
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    onCategoryClick = {
                        navController.navigate(CategoryRoute)
                    },
                    onProfileClick = {
                        navController.navigate(ProfileRoute)
                    },
                )
            }

            composable<CategoryRoute> {
                CategoryScreen(
                    onDetailClick = { name, stock ->
                        navController.navigate(
                            DetailCategoryRoute(
                                name = name,
                                stock = stock,
                            )
                        )
                    },
                )
            }

            composable<DetailCategoryRoute> { backStackEntry ->
                val detail = backStackEntry.toRoute<DetailCategoryRoute>()
                DetailCategoryScreen(
                    name = detail.name,
                    stock = detail.stock,
                    onHomeClick = {
                        navController.navigate(HomeRoute) {
                            popUpTo<HomeRoute> {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable<ProfileRoute> {
                ProfileScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AppShellPreview() {
    MyNavigationComposeTheme {
        AppShell()
    }
}
