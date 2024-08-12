package com.example.foodies.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodies.R
import com.example.foodies.ui.theme.FoodiesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcceptedOrderAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.counter_button_container)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = dimensionResource(id = R.dimen.elevation_large)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.alert_dialog_card_height))
                .padding(dimensionResource(id = R.dimen.padding_large))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Ваш заказ принят. Ожидайте доставки",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                )
                ElevatedButton(
                    onClick = { onDismissRequest() },
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(dimensionResource(id = R.dimen.elevation_large)),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_button_content)),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange)),
                    modifier = modifier
                ) {
                    Text(
                        text = "Ок",
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AlertPreview() {
    FoodiesTheme {
        AcceptedOrderAlertDialog(onDismissRequest = { /*TODO*/ })
    }
}
