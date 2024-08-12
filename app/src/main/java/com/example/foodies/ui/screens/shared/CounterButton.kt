package com.example.foodies.ui.screens.shared

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.data.model.Dish


/**
 * A counter button
 *
 * Has dish quantity value and an ability to increase/decrease it
 * Is used when quantity > 0
 *
 */
@Composable
fun DishItemCounterButton(
    dish: Dish,
    onAddButtonClick: (Dish) -> Unit,
    onReduceButtonClick: (Dish) -> Unit,
    buttonSize: Dp,
    buttonContainerColor: Color,
    modifier: Modifier = Modifier,
    spaceBetween: Dp = 0.dp,
    buttonElevation: Dp = 0.dp
) {
    Row(
        horizontalArrangement = if (spaceBetween == 0.dp) Arrangement.SpaceBetween else Arrangement.spacedBy(spaceBetween),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        ElevatedButtonWithIcon(
            onClick = { onReduceButtonClick(dish) },
            iconRes = R.drawable.minus,
            iconDescription = R.string.reduce_button,
            buttonContainerColor = buttonContainerColor,
            buttonElevation = buttonElevation,
            modifier = Modifier.size(buttonSize)
        )

        Text(
            text = "${dish.quantity}",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(dimensionResource(id = R.dimen.counter_button_quantity_width))
        )

        ElevatedButtonWithIcon(
            onClick = { onAddButtonClick(dish) },
            iconRes = R.drawable.plus,
            iconDescription = R.string.add_button,
            buttonContainerColor = buttonContainerColor,
            buttonElevation = buttonElevation,
            modifier = Modifier.size(buttonSize)
        )
    }
}


/**
 *
 */
@Composable
fun ElevatedButtonWithIcon(
    onClick: () -> Unit,
    @DrawableRes iconRes: Int,
    @StringRes iconDescription: Int,
    buttonContainerColor: Color,
    modifier: Modifier = Modifier,
    buttonElevation: Dp = 0.dp
) {
    ElevatedButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(buttonElevation),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_button_content)),
        colors = ButtonDefaults.buttonColors(containerColor = buttonContainerColor),
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = stringResource(id = iconDescription),
            tint = colorResource(id = R.color.orange)
        )
    }
}


@Preview
@Composable
fun DishItemCounterButtonPreview() {
    DishItemCounterButton(
        dish = Dish(
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
        onReduceButtonClick = {},
        onAddButtonClick = {},
        buttonSize = dimensionResource(id = R.dimen.cart_button_height),
        buttonContainerColor = Color.White,
        buttonElevation = dimensionResource(R.dimen.elevation_medium),
        modifier = Modifier
    )
}
