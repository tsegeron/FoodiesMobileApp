package com.example.foodies.ui.screens.cart

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodies.R
import com.example.foodies.data.model.Dish
import com.example.foodies.presentation.FoodiesViewModel
import com.example.foodies.ui.screens.shared.BottomBar
import com.example.foodies.ui.screens.shared.EmptyResultsBoxLayout


@Composable
fun CartScreen(
    onBackActionButtonClick: () -> Unit,
    onAlertDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    appViewModel: FoodiesViewModel = viewModel()
) {
    val foodiesUiState by appViewModel.uiState.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CartScreenTopAppBar(onBackActionButtonClick = onBackActionButtonClick) },
        bottomBar = {
            if (foodiesUiState.dishesInCartList.isNotEmpty()) {
                BottomBar(
                    totalCost = foodiesUiState.totalCost,
                    onClick = { openAlertDialog.value = true }
                )
            }
        },
        containerColor = Color.White,
        modifier = modifier
    ) { innerPadding ->
        if (foodiesUiState.dishesInCartList.isEmpty()) {
            EmptyResultsBoxLayout(
                textResId = R.string.empty_cart,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            CartDishItemsList(
                dishesInCart = foodiesUiState.dishesInCartList.toList(),
                onAddButtonClick = { appViewModel.increaseDishCounter(it) },
                onReduceButtonClick = { appViewModel.decreaseDishCounter(it) },
                modifier = Modifier,
                contentPadding = innerPadding
            )
        }

        if (openAlertDialog.value) {
            AcceptedOrderAlertDialog(
                onDismissRequest = {
                    onAlertDismissRequest()
                    openAlertDialog.value = false
                }
            )
        }
    }
}

@Composable
fun CartDishItemsList(
    dishesInCart: List<Dish>,
    onAddButtonClick: (Dish) -> Unit,
    onReduceButtonClick: (Dish) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = contentPadding,
        modifier = modifier.fillMaxWidth()
    ) {
        items(dishesInCart) { dish ->
            CartDishItem(
                dish = dish,
                onAddButtonClick = onAddButtonClick,
                onReduceButtonClick = onReduceButtonClick
            )
        }
    }
}
