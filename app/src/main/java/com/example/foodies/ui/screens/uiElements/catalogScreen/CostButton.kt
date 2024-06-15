package com.example.foodies.ui.screens.uiElements.catalogScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.data.model.Dish
import com.example.foodies.ui.screens.uiElements.DishItemCounterButton


/**
 * A cost button
 *
 * Has current dish price value and the old dish price if it's given
 * Is used when dish quantity is 0
 *
 */
@Composable
fun DishItemCostButton(
    dish: Dish,
    onClick: (dish: Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = { onClick(dish) },
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(dimensionResource(R.dimen.elevation_medium)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier
//            .align(Alignment.CenterHorizontally)
//            .padding(top = dimensionResource(id = R.dimen.padding_medium))
//            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.wrapContentHeight()
        ) {
            Text(
                text = "${dish.price_current} ₽", // TODO: Change to dynamic currency?
                style = MaterialTheme.typography.bodyLarge
            )
            if (dish.price_old != null) {
                Text(
                    text = "${dish.price_old} ₽", // TODO: Manage big values
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
