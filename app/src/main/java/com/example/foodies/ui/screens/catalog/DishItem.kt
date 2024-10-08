package com.example.foodies.ui.screens.catalog

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodies.R
import com.example.foodies.data.model.Dish
import com.example.foodies.ui.screens.shared.DishItemCounterButton
import com.example.foodies.ui.theme.FoodiesTheme


private val tagsMap = mapOf(
    "Вегетарианское блюдо" to R.drawable.vegan,
    "Острое" to R.drawable.spicy,
    "Со скидкой" to R.drawable.discount
)


@Composable
fun DishItem(
    dish: Dish,
    onDishCardClick: (Int) -> Unit,
    onCostButtonClick: (dish: Dish) -> Unit,
    onCounterAddButtonClick: (dish: Dish) -> Unit,
    onCounterReduceButtonClick: (dish: Dish) -> Unit,
    tagsNamesList: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        onClick = { onDishCardClick(dish.id) },
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.counter_button_container)),
        modifier = modifier
            .fillMaxWidth()
    ) {
        DishItemImage(
            dishImage = dish.image,
            dishTags = tagsNamesList,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.dish_item_image_height))
        )

        DishItemInfoAndButton(
            dish = dish,
            onCostButtonClick = onCostButtonClick,
            onCounterAddButtonClick = onCounterAddButtonClick,
            onCounterReduceButtonClick = onCounterReduceButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium))
        )
    }
}


@Composable
fun DishItemImage(
    dishImage: String,
    dishTags: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.dish), // TODO: change to image from API, coil
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )
        Row {
            dishTags.forEach { tag ->
                val tagIcon = tagsMap[tag]

                if (tagIcon != null) {
                    DishItemTags(
                        painterRes = tagIcon,
                        tagDescription = tag
                    )
                }
            }
        }
    }
}

@Composable
fun DishItemTags(
    @DrawableRes painterRes: Int,
    tagDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(
                start = dimensionResource(id = R.dimen.padding_small),
                top = dimensionResource(id = R.dimen.padding_small)
            )
            .size(dimensionResource(id = R.dimen.tag_size))
    ) {
        Image(
            painter = painterResource(id = painterRes),
            contentDescription = tagDescription,
        )
    }
}

@Composable
fun DishItemInfoAndButton(
    dish: Dish,
    onCostButtonClick: (dish: Dish) -> Unit,
    onCounterAddButtonClick: (dish: Dish) -> Unit,
    onCounterReduceButtonClick: (dish: Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = dish.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_extra_small))
                .focusable()
        )
        Text(
            text = "${dish.measure} ${dish.measure_unit}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        if (dish.quantity == 0) {
            DishItemCostButton(
                dish = dish,
                onClick = onCostButtonClick,
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth()
            )
        } else {
            DishItemCounterButton(
                dish = dish,
                onAddButtonClick = onCounterAddButtonClick,
                onReduceButtonClick = onCounterReduceButtonClick,
                buttonSize = dimensionResource(id = R.dimen.cart_button_height),
                buttonContainerColor = Color.White,
                buttonElevation = dimensionResource(R.dimen.elevation_medium),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
                    .fillMaxWidth()
            )
        }
    }
}


@Preview
@Composable
fun DishItemPreview() {
    FoodiesTheme {
        DishItem(
            Dish(
                1,
                676168,
                "Авокадо Кранч Маки 8шт",
                "Ролл с нежным мясом камчатского краба, копченой курицей и авокадо.Украшается соусом\"Унаги\" и легким майонезом  Комплектуется бесплатным набором для роллов (Соевый соус Лайт 35г., васаби 6г., имбирь 15г.). +1 набор за каждые 600 рублей в заказе",
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
            listOf("Вегетарианское блюдо", "Острое", "asdad")
        )
    }
}
