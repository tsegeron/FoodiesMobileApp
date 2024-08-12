package com.example.foodies.ui.screens.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.foodies.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodies.data.model.Dish
import com.example.foodies.data.model.Tag
import com.example.foodies.presentation.FoodiesViewModel
import com.example.foodies.ui.screens.shared.BottomBar
import com.example.foodies.ui.screens.shared.EmptyResultsBoxLayout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onSearchIconClick: () -> Unit,
    onDishCardClick: (Int) -> Unit,
    onCartButtonClick: () -> Unit,
    appViewModel: FoodiesViewModel = viewModel()
) {
    val foodiesUiState by appViewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            CatalogScreenTopAppBar(
                markedTagsNum = appViewModel.getSelectedTagsCount(),
                categories = foodiesUiState.categoriesList,
                onCategoryChipClick = { appViewModel.markCategory(it) },
                onFilterIconClick = { isSheetOpen = !isSheetOpen },
                onSearchIconClick = onSearchIconClick
            )
        },
        bottomBar = {
            if (foodiesUiState.totalCost != 0) {
                BottomBar(
                    totalCost = foodiesUiState.totalCost,
                    onClick = onCartButtonClick
                )
            }
        },
        containerColor = Color.White,
    ) { innerPadding ->
        if (foodiesUiState.dishesByFilterList.isEmpty()) {
            EmptyResultsBoxLayout(
                textResId = R.string.catalog_no_results,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            DishesList(
                dishesList = foodiesUiState.dishesByFilterList,
                tagsList = foodiesUiState.tagsList,
                onDishCardClick = onDishCardClick,
                onCostButtonClick = { appViewModel.addDishToCart(it) },
                onCounterAddButtonClick = { appViewModel.increaseDishCounter(it) },
                onCounterReduceButtonClick = { appViewModel.decreaseDishCounter(it) },
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_large),
                    top = dimensionResource(id = R.dimen.padding_small),
                    end = dimensionResource(id = R.dimen.padding_large)
                ),
                contentPadding = innerPadding
            )
        }
        if (isSheetOpen) {
            FilterMenu(
                tagsList = foodiesUiState.tagsList,
                modalSheetState = sheetState,
                filterMenuOnDismissRequest = { isSheetOpen = !isSheetOpen },
                applyFilterOptionsOnClick = {
                    appViewModel.applyFilterChanges(it)
                    isSheetOpen = !isSheetOpen
                },
                modifier = Modifier
            )
        }
    }
}

@Composable
fun DishesList(
    dishesList: List<Dish>,
    tagsList: List<Tag>,
    onDishCardClick: (Int) -> Unit,
    onCostButtonClick: (dish: Dish) -> Unit,
    onCounterAddButtonClick: (dish: Dish) -> Unit,
    onCounterReduceButtonClick: (dish: Dish) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_card_vertical)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_card_horizontal)),
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        items(dishesList) { dish ->
            DishItem(
                dish = dish,
                onDishCardClick = onDishCardClick,
                onCostButtonClick = onCostButtonClick,
                onCounterAddButtonClick = onCounterAddButtonClick,
                onCounterReduceButtonClick = onCounterReduceButtonClick,
                tagsNamesList = tagsList.filter { it.id in dish.tag_ids }.map { it.name }
            )
        }
    }
}
