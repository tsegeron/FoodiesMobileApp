package com.example.foodies.ui.screens.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.example.foodies.R


@Composable
fun BottomBar(
    totalCost: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonPrompt: String = ""
) {
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
        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.orange)),
            modifier = Modifier.fillMaxSize()
        ) {
            if (buttonPrompt.isEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.cart),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_small)))
            Text(
                text = "$buttonPrompt $totalCost ₽",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}
