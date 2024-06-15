package com.example.foodies.ui.screens.uiElements.cartScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.ui.theme.FoodiesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogOnClick(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(16.dp)
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
                    modifier = Modifier.padding(16.dp),
                )
                ElevatedButton(
                    onClick = { onDismissRequest() },
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_button_content)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF15412)),
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
        AlertDialogOnClick(onDismissRequest = { /*TODO*/ })
    }
}
