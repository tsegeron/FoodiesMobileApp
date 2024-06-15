package com.example.foodies


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.foodies.data.model.Dish
import com.example.foodies.presentation.FoodiesViewModel
import com.example.foodies.ui.screens.CartScreen
import com.example.foodies.ui.screens.CatalogScreen
import com.example.foodies.ui.screens.DishScreen
import com.example.foodies.ui.screens.SearchScreen
import kotlinx.coroutines.delay


enum class FoodiesScreen(@StringRes val title : Int) {
    Splash(title = R.string.splash),
    Catalog(title = R.string.catalog),
    DishCard(title = R.string.dish_card),
    Cart(title = R.string.cart),
    Search(title = R.string.search)
}



@Composable
fun FoodiesApp(
    appViewModel: FoodiesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
//    val backStackEntry by navController.currentBackStackEntryAsState()
//    val currentScreen = FoodiesScreen.valueOf(
//        backStackEntry?.destination?.route ?: FoodiesScreen.Catalog.name
//    )

    NavHost(
        navController = navController,
        startDestination = FoodiesScreen.Splash.name,
    ) {
        composable(route = FoodiesScreen.Splash.name) {
            SplashScreenAnimation(
                navigateToCatalog = { navController.navigate(FoodiesScreen.Catalog.name) }
            )
        }
        composable(route = FoodiesScreen.Catalog.name) {
            CatalogScreen(
                onSearchIconClick = { navController.navigate(FoodiesScreen.Search.name) },
                onDishCardClick = {
                    navController.navigate("${FoodiesScreen.DishCard.name}/$it")
                },
                onCartButtonClick = { navController.navigate(FoodiesScreen.Cart.name) },
                appViewModel = appViewModel
            )
        }
        composable(route = FoodiesScreen.Search.name) {
            SearchScreen(
                onDishCardClick = {
                    navController.navigate("${FoodiesScreen.DishCard.name}/$it")
                },
                onCartButtonClick = { navController.navigate(FoodiesScreen.Cart.name) },
                onBackButtonClick = { navController.navigateUp() },
                appViewModel = appViewModel
            )
        }
        composable(
            route = "${FoodiesScreen.DishCard.name}/{dish_id}",
            arguments = listOf(
                navArgument("dish_id") {
                    type = NavType.IntType
                }
            )
        ) {
            val dishId = it.arguments?.getInt("dish_id")?: 0
            DishScreen(
                dish = appViewModel.uiState.collectAsState().value.dishesList.find { it.id == dishId },
                onBackActionButtonClick = { navController.navigateUp() },
                onCartButtonClick = { navController.navigate(FoodiesScreen.Cart.name) },
                onCostButtonClick = { appViewModel.addDishToCart(it) },
                onCounterAddButtonClick = { appViewModel.increaseDishCounter(it) },
                onCounterReduceButtonClick = { appViewModel.decreaseDishCounter(it) }
            )
        }
        composable(route = FoodiesScreen.Cart.name) {
            CartScreen(
                onBackActionButtonClick = { navController.navigateUp() },
                onAlertDismissRequest = {
                    appViewModel.emptyCart()
                    navController.navigate(FoodiesScreen.Catalog.name)
                },
                appViewModel = appViewModel,
            )
        }
    }
}

@Composable
fun SplashScreenAnimation(
    navigateToCatalog: () -> Unit
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("anim.json"),
    )
    LaunchedEffect(key1 = true) {
        delay(4000)
        navigateToCatalog()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFE5F0F))
    ) {
        LottieAnimation(
            composition = composition,
            restartOnPlay = false
        )
    }
}

//@Preview
//@Composable
//fun FoodiesAppPreview() {
//    FoodiesTheme {
//        FoodiesApp()
//    }
//}
