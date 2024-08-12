package com.example.foodies.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodies.R
import com.example.foodies.data.model.Dish
import com.example.foodies.ui.screens.shared.BottomBar
import com.example.foodies.ui.screens.shared.DishItemCounterButton
import com.example.foodies.ui.theme.FoodiesTheme


@Composable
fun DishScreen(
    dish: Dish?,
    onBackActionButtonClick: () -> Unit,
    onCostButtonClick: (dish: Dish) -> Unit,
    onCartButtonClick: () -> Unit,
    onCounterAddButtonClick: (dish: Dish) -> Unit,
    onCounterReduceButtonClick: (dish: Dish) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (dish != null) {
        Scaffold(
            bottomBar = {
                DishScreenBottomBar(
                    dish = dish,
                    onCostButtonClick = onCostButtonClick,
                    onCartButtonClick = onCartButtonClick,
                    onCounterAddButtonClick = onCounterAddButtonClick,
                    onCounterReduceButtonClick = onCounterReduceButtonClick
                )
            },
            containerColor = Color.White,
            modifier = modifier
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                DishImageAndActionButton(actionButtonOnClick = onBackActionButtonClick)
                DishCompositionColumn(dish = dish)
            }
        }
    }
}

@Composable
fun DishScreenBottomBar(
    dish: Dish,
    onCostButtonClick: (dish: Dish) -> Unit,
    onCartButtonClick: () -> Unit,
    onCounterAddButtonClick: (dish: Dish) -> Unit,
    onCounterReduceButtonClick: (dish: Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    if (dish.quantity == 0){
        BottomBar(
            totalCost = dish.price_current,
            buttonPrompt = stringResource(id = R.string.add_to_cart1),
            onClick = { onCostButtonClick(dish) }
        )
    } else {
        BottomAppBar(
            modifier = modifier.shadow(dimensionResource(id = R.dimen.bottom_bar_shadow)),
            containerColor = Color.White,
            contentPadding = PaddingValues(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large),
                top = dimensionResource(id = R.dimen.padding_medium),
                bottom = dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            DishItemCounterButton(
                dish = dish,
                onAddButtonClick = onCounterAddButtonClick,
                onReduceButtonClick = onCounterReduceButtonClick,
                buttonSize = dimensionResource(id = R.dimen.cart_button_height),
                buttonContainerColor = colorResource(id = R.color.counter_button_container),
                modifier = Modifier.width(dimensionResource(id = R.dimen.counter_button_width))
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.bottom_bar_spacer_width)))

            Button(
                onClick = onCartButtonClick,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange)),
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
                Text(
                    text = "${dish.price_current * dish.quantity} ₽",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DishImageAndActionButton(
    actionButtonOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        FloatingActionButton(
            onClick = actionButtonOnClick,
            shape = MaterialTheme.shapes.extraLarge,
            containerColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = dimensionResource(id = R.dimen.elevation_medium)
            ),
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_large),
                    start = dimensionResource(id = R.dimen.padding_large)
                )
                .size(dimensionResource(id = R.dimen.catalog_button_height))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrowleft),
                contentDescription = null
            )
        }
        Image(
            painter = painterResource(id = R.drawable.dish),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun DishCompositionColumn(
    dish: Dish,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_extra_large),
                bottom = dimensionResource(id = R.dimen.padding_extra_large),
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineLarge,
                color = colorResource(id = R.color.dish_screen_name)
            )
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.dish_screen_description),
                textAlign = TextAlign.Justify
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.horizontal_divider_width))
                .fillMaxWidth(),
            color = colorResource(id = R.color.horizontal_divider)
        )
        
        DishInfoRow(
            propertyDescription = R.string.measure,
            propertyValue = "${dish.measure} ${dish.measure_unit}"
        )
        DishInfoRow(
            propertyDescription = R.string.energy_per_100_grams,
            propertyValue = "${dish.energy_per_100_grams} ${dish.measure_unit}"
        )
        DishInfoRow(
            propertyDescription = R.string.proteins_per_100_grams,
            propertyValue = "${dish.proteins_per_100_grams} ${dish.measure_unit}"
        )
        DishInfoRow(
            propertyDescription = R.string.fats_per_100_grams,
            propertyValue = "${dish.fats_per_100_grams} ${dish.measure_unit}"
        )
        DishInfoRow(
            propertyDescription = R.string.carbohydrates_per_100_grams,
            propertyValue = "${dish.carbohydrates_per_100_grams} ${dish.measure_unit}"
        )
    }
}

@Composable
fun DishInfoRow(
    @StringRes propertyDescription: Int,
    propertyValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.dish_info_row_height))
            .padding(
                start = dimensionResource(id = R.dimen.padding_large),
                end = dimensionResource(id = R.dimen.padding_large)
            )
    ) {
        Text(
            text = stringResource(id = propertyDescription),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.dish_screen_description)

        )
        Text(
            text = propertyValue,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.dish_screen_name)
        )
    }
    HorizontalDivider(
        modifier = Modifier
            .height(dimensionResource(id = R.dimen.horizontal_divider_width))
            .fillMaxWidth(),
        color = colorResource(id = R.color.horizontal_divider)
    )
}


@Preview(name = "", showBackground = true)
@Composable
fun DishScreenBottomBarPreview() {
    DishScreenBottomBar(
        Dish(
            1,
            676168,
            "Авокадо Кранч Маки 8шт",
            "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо. Украшается соусом \"Унаги\" и легким майонезом. Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
            "1.jpg",
            47000,
            null,
            250,
            "г",
            307.8,
            6.1,
            11.4,
            45.1,
            listOf(4),
            3
        ),
        onCounterAddButtonClick = {},
        onCounterReduceButtonClick = {},
        onCartButtonClick = {},
        onCostButtonClick = {}
    )
}

@Preview
@Composable
fun DishScreenPreview() {
    FoodiesTheme {
        DishScreen(
            Dish(
                1,
                676168,
                "Авокадо Кранч Маки 8шт",
                "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо. Украшается соусом \"Унаги\" и легким майонезом. Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
                "1.jpg",
                47000,
                null,
                250,
                "г",
                307.8,
                6.1,
                11.4,
                45.1,
                listOf(4),
                0
            ),
            {},
            {},
            {},
            {},
            {}
        )
    }
}
