package com.example.foodies.ui.screens.uiElements.cartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.data.model.Dish
import com.example.foodies.ui.screens.uiElements.DishItemCounterButton

@Composable
fun CartDishItem(
    dish: Dish,
    onAddButtonClick: (Dish) -> Unit,
    onReduceButtonClick: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.dish), // TODO: change to actual image
            contentDescription = null,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .size(dimensionResource(id = R.dimen.cart_dish_image_size))
        )

        CartDishInfoColumn(
            dish = dish,
            onAddButtonClick = onAddButtonClick,
            onReduceButtonClick = onReduceButtonClick,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_large),
                    bottom = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large)
                )
                .height(dimensionResource(id = R.dimen.cart_dish_image_size))
        )
    }
    HorizontalDivider(
        modifier = Modifier.height(1.dp).fillMaxWidth(),
        color = Color(0xFFE0E0E0)
    )
}

@Composable
fun CartDishInfoColumn(
    dish: Dish,
    onAddButtonClick: (Dish) -> Unit,
    onReduceButtonClick: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = dish.name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .height(dimensionResource(id = R.dimen.catalog_button_height))
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimensionResource(id = R.dimen.padding_medium))
        ) {
            DishItemCounterButton(
                dish = dish,
                onAddButtonClick = onAddButtonClick,
                onReduceButtonClick = onReduceButtonClick,
                spaceBetween = dimensionResource(id = R.dimen.padding_small),
                buttonSize = dimensionResource(id = R.dimen.cart_button_height),
                buttonContainerColor = Color(0xFFF5F5F5)
            )
            DishCostColumn(
                dishPriceCurrent = dish.price_current,
                dishPriceOld = dish.price_old
            )
        }
    }
}

@Composable
fun DishCostColumn(
    dishPriceCurrent: Int,
    dishPriceOld: Int?,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = "$dishPriceCurrent ₽", // TODO: change to dynamic currency?
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        if (dishPriceOld != null) {
            Text(
                text = "$dishPriceOld ₽",
                style = MaterialTheme.typography.labelMedium,
                lineHeight = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun CartDishItemPreview() {
    CartDishItem(
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
        {},
        {}
    )
}
